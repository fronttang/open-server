package com.xmzy.bank.controller;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmzy.bank.constant.BankCacheConstants;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBController;
import com.xmzy.bank.ghb.GHBRequestHeader;
import com.xmzy.bank.ghb.GHBTransCode;
import com.xmzy.bank.ghb.api.req.CustRegisterNotifyParam;
import com.xmzy.bank.ghb.api.req.GetFileTemporaryUrlParam;
import com.xmzy.bank.ghb.api.req.GetSecretKeyParam;
import com.xmzy.bank.ghb.api.req.PlatformSettlementNotifyParam;
import com.xmzy.bank.ghb.api.req.RechargeNotifyParam;
import com.xmzy.bank.ghb.api.req.ReplyFileNotifyParam;
import com.xmzy.bank.ghb.api.resp.CustRegisterNotifyVo;
import com.xmzy.bank.ghb.api.resp.GetFileTemporaryUrlVo;
import com.xmzy.bank.ghb.api.resp.GetSecretKeyVo;
import com.xmzy.bank.ghb.api.resp.PlatformSettlementNotifyVo;
import com.xmzy.bank.ghb.api.resp.RechargeNotifyVo;
import com.xmzy.bank.ghb.api.resp.ReplyFileNotifyVo;
import com.xmzy.bank.ghb.cipher.GHBRequestAesKeyCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseAesKeyCipher;
import com.xmzy.bank.ghb.event.CustRegisterNotifyEvent;
import com.xmzy.bank.ghb.event.PlatformSettlementNotifyEvent;
import com.xmzy.bank.ghb.event.RechargeNotifyEvent;
import com.xmzy.bank.ghb.event.ReplyFileNotifyEvent;
import com.xmzy.base.Result;
import com.xmzy.base.util.RedisUtil;
import com.xmzy.model.base.enums.ReturnFileStatus;
import com.xmzy.mq.util.KafkaUtils;
import com.xmzy.server.base.context.IRequestContext;
import com.xmzy.server.base.context.RequestContextHolder;
import com.xmzy.util.api.IAliyunOSSApi;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 提供给华兴银行的接口</br>
 * 所有业务错误全部抛出{@link GHBBusinessException}
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@RestController
@GHBController
@RequestMapping("/ghb")
public class GHBControllers extends BankBaseController implements ApplicationEventPublisherAware {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private IAliyunOSSApi ossApi;

    /**
     * 生成加密密钥
     * 
     * @param request
     * @return
     */
    @PostMapping("/secret/generate")
    @GHBController(decoder = GHBRequestAesKeyCipher.class, encoder = GHBResponseAesKeyCipher.class)
    @Deprecated
    public GetSecretKeyVo secretKeyGenerate(@RequestBody @Valid GetSecretKeyParam request) {

        SecretKey generateKey = SecureUtil.generateKey("SM4");
        String secretKey = HexUtil.encodeHexStr(generateKey.getEncoded()).toUpperCase();

        String applyTime = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN);

        IRequestContext current = RequestContextHolder.getCurrent();
        GHBRequestHeader header = (GHBRequestHeader)current.getItem(GHBConstants.REQUEST_HEADER_CONTEXT_KEY);

        if (Objects.nonNull(header)) {
            RedisUtil.set(BankCacheConstants.getSecretKey(header.getAppId()), secretKey);
        }

        return new GetSecretKeyVo(secretKey, applyTime);
    }

    /**
     * 客户注册成功通知(异步处理)
     * 
     * @param request
     * @return
     */
    @PostMapping("/notify/cust/registered")
    public CustRegisterNotifyVo custRegisterNotify(@RequestBody @Valid CustRegisterNotifyParam request) {

        // 事件发布（用于写日志或其他操作）
        publisher.publishEvent(new CustRegisterNotifyEvent(request));

        // 写入队列由消费者通知到webt
        KafkaUtils.sendMessage(GHBConstants.CUST_REGISTER_NOTIFY_TOPIC, request);

        CustRegisterNotifyVo result = new CustRegisterNotifyVo();
        result.setReturnCode(GHBResultEnum.REQ_SUCCESS.getErrorCode());
        result.setReturnMessage(GHBResultEnum.REQ_SUCCESS.getErrorMsg());

        return result;
    }

    /**
     * 平台结算结果通知(异步处理)
     * 
     * @param request
     * @return
     */
    @PostMapping("/notify/platform/settlement")
    public PlatformSettlementNotifyVo
        platformSettlementNotify(@RequestBody @Valid PlatformSettlementNotifyParam request) {

        // 事件发布，可用于记录日志啥的
        publisher.publishEvent(new PlatformSettlementNotifyEvent(request));

        // 写入队列由消费者通知到webt
        KafkaUtils.sendMessage(GHBConstants.PLATFORM_SETTLEMENT_NOTIFY_TOPIC, request);

        PlatformSettlementNotifyVo result = new PlatformSettlementNotifyVo();
        result.setBankId(GHBConstants.BANK_ID);
        result.setReqCode(GHBResultEnum.REQ_SUCCESS.getErrorCode());
        result.setReqMsg(GHBResultEnum.REQ_SUCCESS.getErrorMsg());

        return result;
    }

    /**
     * 充值成功通知(异步处理)
     * 
     * @param request
     * @return
     */
    @PostMapping("/notify/recharge")
    public RechargeNotifyVo rechargeNotify(@RequestBody @Valid RechargeNotifyParam request) {

        // 事件发布，可用于记录日志啥的
        publisher.publishEvent(new RechargeNotifyEvent(request));

        // 写入队列由消费者通知到webt
        KafkaUtils.sendMessage(GHBConstants.RECHARGE_NOTIFY_TOPIC, request);

        RechargeNotifyVo result = new RechargeNotifyVo();
        result.setReturnCode(GHBResultEnum.REQ_SUCCESS.getErrorCode());
        result.setReturnMessage(GHBResultEnum.REQ_SUCCESS.getErrorMsg());

        return result;
    }

    /**
     * 回盘文件通知(异步处理)
     * 
     * @param request
     * @return
     */
    @PostMapping("/notify/file/result")
    public ReplyFileNotifyVo replyFileNotify(@RequestBody @Valid ReplyFileNotifyParam request) {

        // 事件发布，可用于记录日志啥的
        publisher.publishEvent(new ReplyFileNotifyEvent(request));

        // 写队列异步处理,这里需要下载文件并上传到OSS,比较耗时
        KafkaUtils.sendMessage(GHBConstants.REPLY_FILE_NOTIFY_TOPIC, request);

        ReplyFileNotifyVo result = new ReplyFileNotifyVo();
        result.setReqCode(GHBResultEnum.REQ_SUCCESS.getErrorCode());
        result.setReqMsg(GHBResultEnum.REQ_SUCCESS.getErrorMsg());
        result.setTranStatus(ReturnFileStatus.ING.code());// 处理中
        result.setTransCode(GHBTransCode.FCMT0008);
        result.setBankId(GHBConstants.BANK_ID);

        // result.setTransCode(transCode);

        return result;
    }

    @Deprecated
    @PostMapping("/file/download/url")
    public GetFileTemporaryUrlVo getFileTemporaryUrl(@RequestBody @Valid GetFileTemporaryUrlParam request) {

        Result<String> result = ossApi.getTempUrl(request.getFilePath(), 10);

        if (Objects.nonNull(result) && result.isSuccess()) {
            return new GetFileTemporaryUrlVo(result.getData());
        }

        GetFileTemporaryUrlVo resultVo = new GetFileTemporaryUrlVo();
        resultVo.setReturnCode(GHBResultEnum.BUSINESS_ERROR.errorCode());
        resultVo.setReturnMessage(GHBResultEnum.BUSINESS_ERROR.errorMsg());

        return resultVo;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
