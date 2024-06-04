package com.xmzy.bank.ghb.api;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.alibaba.fastjson.JSON;
import com.xmzy.api.AbstractHttpClientApi;
import com.xmzy.api.util.ApiUtils;
import com.xmzy.bank.constant.GHBProperties;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBRequestHeader;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.GHBResponseHeader;
import com.xmzy.bank.ghb.IGHBApi;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.IGHBResponseBody;
import com.xmzy.bank.ghb.cipher.GHBRequestCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;
import com.xmzy.bank.ghb.event.GHBRequestEvent;
import com.xmzy.bank.ghb.event.GHBResponseEvent;
import com.xmzy.bank.ghb.util.SecretKeyHolder;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.server.base.util.HttpRequestUtil;
import com.xmzy.server.base.util.RestTemplateUtils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象银行接口调用
 * 
 * @author fronttang
 * @date 2021/08/27
 */
@Slf4j
public abstract class AbstractGHBApi<P extends IGHBRequestBody, R extends IGHBResponseBody>
    extends AbstractHttpClientApi<P, R> implements IGHBApi<P, R>, ApplicationEventPublisherAware {

    /**
     * 应用私钥 （小马生成）
     */
    protected String privateKey;

    /**
     * 平台公钥（银行提供）
     */
    protected String publicKey;

    @Autowired
    protected GHBProperties properties;

    protected ApplicationEventPublisher publisher;

    @Override
    public R doExecute(P param, boolean retry) throws BusinessException {

        try {

            return sendRequest(param);

        } catch (GHBBusinessException e) {
            log.error("", e);
            // 如果发现是 3001/3002 进行重试
            if (GHBResultCode.SECRET_KEY_ERROR.equals(e.getErrorCode())
                || GHBResultCode.DECRYPT_ERROR.equals(e.getErrorCode())) {
                if (retry) {
                    GetSecretKeyApi.exec();
                    return execute(param, false);
                }
            }
            throw new BusinessException(e);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException("银行接口调用失败");
        }
    }

    protected R sendRequest(P param) {

        // 执行调用
        GHBResponse result = RestTemplateUtils.exchange(getRequestUri(param), this.method, getRequestHeader(),
            getRequestBody(param), GHBResponse.class);

        // 后置处理
        R res = handleResult(param, result);

        return res;
    }

    /**
     * 处理返回
     * 
     * @param param
     * @param result
     * @return
     */
    protected R handleResult(P param, GHBResponse result) {

        GHBResponseHeader header = result.getHeader();

        String errorCode = header.getErrorCode();

        if (GHBResultCode.SECRET_KEY_ERROR.equals(errorCode)) {
            publisher.publishEvent(new GHBResponseEvent(result));
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        } else if (GHBResultCode.DECRYPT_ERROR.equals(errorCode)) {
            publisher.publishEvent(new GHBResponseEvent(result));
            throw new GHBBusinessException(GHBResultEnum.DECRYPT_ERROR);
        }

        if (!isSuccess(header)) {
            publisher.publishEvent(new GHBResponseEvent(result));
            throw new BusinessException(header);
        }

        // 对返回参数进行解密
        IGHBCipher<GHBResponse> cipher = getResponseCipher();
        result = cipher.decode(result);

        publisher.publishEvent(new GHBResponseEvent(result));

        R res = JSON.parseObject(Convert.toStr(result.getBody()), getResponseType());
        return res;
    }

    /**
     * 
     * @param header
     * @return
     */
    protected boolean isSuccess(GHBResponseHeader header) {
        return header.isSuccess();
    }

    /**
     * 取得返回包装类
     * 
     * @param response
     * @return
     */
    protected IGHBCipher<GHBResponse> getResponseCipher() {
        String secretKey = SecretKeyHolder.getSecretKey();
        if (StrUtil.isBlank(secretKey)) {
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        }
        return new GHBResponseCipher(publicKey, privateKey, secretKey);
    }

    /**
     * 构建请求参数
     * 
     * @param param
     * @return
     */
    @Override
    protected String getRequestBody(P param) {
        GHBRequest request = getGHBRequest(param);
        return JSON.toJSONString(request);
    }

    protected GHBRequest getGHBRequest(P param) {
        GHBRequest request = new GHBRequest();
        GHBRequestHeader header = new GHBRequestHeader();
        request.setHeader(header);

        header.setAppId(properties.getAppId());

        // TODO 先每次调用都生成
        LocalDateTime now = LocalDateTime.now();
        String requestTime = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
        String requestId = requestTime + RandomUtil.randomNumbers(6);

        header.setRequestId(requestId);
        header.setRequestTime(requestTime);

        request.setBody(param);

        // 对请求参数进行加密
        IGHBCipher<GHBRequest> cipher = getRequestCipher();

        request.setApi(ApiUtils.getApiName(getClass()));
        publisher.publishEvent(new GHBRequestEvent(request));

        request = cipher.encode(request);

        return request;
    }

    /**
     * 获取请求流水号
     * 
     * @param request
     * @return
     */
    protected String getReqSeqNo(P request) {
        return HttpRequestUtil.getRequestNo();
    }

    /**
     * 得到请求包装类
     * 
     * @param request
     * @return
     */
    protected IGHBCipher<GHBRequest> getRequestCipher() {
        String secretKey = SecretKeyHolder.getSecretKey();
        if (StrUtil.isBlank(secretKey)) {
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        }
        return new GHBRequestCipher(publicKey, privateKey, secretKey);
    }

    @Override
    protected void init() {
        super.init();
        this.publicKey = properties.getPublicKey();
        this.privateKey = properties.getPrivateKey();
        this.host = properties.getHost();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
