package com.xmzy.bank.ghb;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.GHBTestConstants;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.factory.SecretKeyGenerater;
import com.xmzy.bank.ghb.api.req.GetSecretKeyParam;
import com.xmzy.bank.ghb.api.resp.GetSecretKeyVo;
import com.xmzy.bank.ghb.cipher.GHBRequestAesKeyCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseAesKeyCipher;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author FrontTang
 * @date 2021/08/28
 */
@Slf4j
public class SecretKeyGeneraterTester {

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

        GetSecretKeyParam requestBody = new GetSecretKeyParam();

        request.setBody(requestBody);

        log.info("加密前的请求数据:{}", JSON.toJSONString(request));

        GHBRequestAesKeyCipher requestWrapper =
            new GHBRequestAesKeyCipher(GHBTestConstants.XM_PUBLIC_KEY, GHBTestConstants.BANK_PRIVATE_KEY);
        request = requestWrapper.encode(request);

        log.info("加密后的请求数据:{}" + JSON.toJSONString(request));

        SecretKeyGenerater secretKeyGenerate =
            SecretKeyGenerater.build(request, GHBTestConstants.BANK_PUBLIC_KEY, GHBTestConstants.XM_PRIVATE_KEY);
        GHBResponse response = secretKeyGenerate.execute();

        log.info("解密前的返回数据:{}", JSON.toJSONString(response));

        GHBResponseAesKeyCipher responseWrapper =
            new GHBResponseAesKeyCipher(GHBTestConstants.XM_PUBLIC_KEY, GHBTestConstants.BANK_PRIVATE_KEY);
        response = responseWrapper.decode(response);

        log.info("解密后的返回数据:{}", JSON.toJSONString(response));

        GetSecretKeyVo responseBody = JSON.parseObject(String.valueOf(response.getBody()), GetSecretKeyVo.class);
        log.info("解密后的返回BODY数据:{}", JSON.toJSONString(responseBody));

    }
}
