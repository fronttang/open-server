package com.xmzy.bank.ghb;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.GHBTestConstants;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.factory.CustRegisterNotify;
import com.xmzy.bank.ghb.api.req.CustRegisterNotifyParam;
import com.xmzy.bank.ghb.api.resp.CustRegisterNotifyVo;
import com.xmzy.bank.ghb.cipher.GHBRequestCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseCipher;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author FrontTang
 * @date 2021/08/28
 */
@Slf4j
public class CustRegisterNotifyTester {

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

        CustRegisterNotifyParam requestBody = new CustRegisterNotifyParam();
        requestBody.setCustStatus("0");
        requestBody.setMerchantId(String.valueOf(System.currentTimeMillis()));
        requestBody.setParentMerchantId(GHBTestConstants.PARENT_MERCHANT_ID);
        requestBody.setReqSeqNo(String.valueOf(System.currentTimeMillis()));
        requestBody.setUserId("1425328435628511232");

        request.setBody(requestBody);

        // log.info("加密前的请求数据:{}", JSON.toJSONString(request));

        GHBRequestCipher requestWrapper = new GHBRequestCipher(GHBTestConstants.XM_PUBLIC_KEY,
            GHBTestConstants.BANK_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);
        request = requestWrapper.encode(request);

        // log.info("加密后的请求数据:{}", JSON.toJSONString(request));

        CustRegisterNotify custRegisterNotify = CustRegisterNotify.build(request, GHBTestConstants.BANK_PUBLIC_KEY,
            GHBTestConstants.XM_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);
        GHBResponse response = custRegisterNotify.execute();

        // log.info("解密前的返回数据:{}", JSON.toJSONString(response));

        GHBResponseCipher responseWrapper = new GHBResponseCipher(GHBTestConstants.XM_PUBLIC_KEY,
            GHBTestConstants.BANK_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);
        response = responseWrapper.decode(response);

        // log.info("解密后的返回数据:{}", JSON.toJSONString(response));

        CustRegisterNotifyVo responseBody =
            JSON.parseObject(String.valueOf(response.getBody()), CustRegisterNotifyVo.class);
        // log.info("解密后的返回BODY数据:{}", JSON.toJSONString(responseBody));

    }
}
