package com.xmzy.bank.ghb;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.GHBTestConstants;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.factory.ReplyFileNotify;
import com.xmzy.bank.ghb.api.req.ReplyFileNotifyParam;
import com.xmzy.bank.ghb.api.resp.CustRegisterNotifyVo;
import com.xmzy.bank.ghb.cipher.GHBRequestCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseCipher;
import com.xmzy.model.base.enums.FileType;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 回盘文件通知接口测试
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@Slf4j
public class ReplyFileNotifyTester {

    public static void main(String[] args) {

        GHBRequest request = new GHBRequest();
        GHBRequestHeader header = new GHBRequestHeader();
        header.setAppId(GHBConstants.BANK_ID);

        LocalDateTime now = LocalDateTime.now();
        String requestTime = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
        String requestId = requestTime + RandomUtil.randomNumbers(6);
        header.setRequestId(requestId);
        header.setRequestTime(requestTime);

        request.setHeader(header);

        ReplyFileNotifyParam requestBody = new ReplyFileNotifyParam();

        requestBody.setFileName("test.txt");
        requestBody.setFilePath("test.txt");
        requestBody.setParentMerchantId(GHBConstants.PARENT_MERCHANT_ID);
        requestBody.setPostingDate("20210902");
        requestBody.setReqSeqNo(IdUtil.fastSimpleUUID());
        requestBody.setThirdBatchNo("1");
        requestBody.setWithholdType(FileType.WALLET.name());

        request.setBody(requestBody);

        log.info("加密前的请求数据:{}", JSON.toJSONString(request));

        GHBRequestCipher requestWrapper = new GHBRequestCipher(GHBTestConstants.XM_PUBLIC_KEY,
            GHBTestConstants.BANK_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);
        request = requestWrapper.encode(request);

        log.info("加密后的请求数据:{}", JSON.toJSONString(request));

        ReplyFileNotify replyFileNotify = ReplyFileNotify.build(request, GHBTestConstants.BANK_PUBLIC_KEY,
            GHBTestConstants.XM_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);

        GHBResponse response = replyFileNotify.execute();

        log.info("解密前的返回数据:{}", JSON.toJSONString(response));

        GHBResponseCipher responseWrapper = new GHBResponseCipher(GHBTestConstants.XM_PUBLIC_KEY,
            GHBTestConstants.BANK_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);
        response = responseWrapper.decode(response);

        log.info("解密后的返回数据:{}", JSON.toJSONString(response));

        CustRegisterNotifyVo responseBody =
            JSON.parseObject(String.valueOf(response.getBody()), CustRegisterNotifyVo.class);
        log.info("解密后的返回BODY数据:{}", JSON.toJSONString(responseBody));

    }
}
