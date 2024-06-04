package com.xmzy.bank.ghb;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.GHBTestConstants;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.req.GetFileTemporaryUrlParam;
import com.xmzy.bank.ghb.cipher.GHBRequestCipher;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取临时访问地址接口测试
 * 
 * @author fronttang
 * @date 2021/09/03
 */
@Slf4j
public class GetFileTemporaryUrlTester {

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

        GetFileTemporaryUrlParam requestBody = new GetFileTemporaryUrlParam();
        requestBody.setFilePath(
            "https://xmzytest01.oss-cn-shenzhen.aliyuncs.com/batch/service/GHB_0000002_SERVICE_20210914.txt?Expires=1631616202&OSSAccessKeyId=LTAI4G7SGJ716ePb7uS9EXJM&Signature=Srv3mBMCHsDauuDVtJv7%2FpANscw%3D");

        request.setBody(requestBody);

        log.info("加密前的请求数据:{}", JSON.toJSONString(request));

        GHBRequestCipher requestWrapper = new GHBRequestCipher(GHBTestConstants.XM_PUBLIC_KEY,
            GHBTestConstants.BANK_PRIVATE_KEY, GHBTestConstants.SECRET_KEY);
        request = requestWrapper.encode(request);

        requestWrapper = new GHBRequestCipher(GHBTestConstants.BANK_PUBLIC_KEY, GHBTestConstants.XM_PRIVATE_KEY,
            GHBTestConstants.SECRET_KEY);
        request = requestWrapper.decode(request);

        String urlencode = Base64.encode(
            "https://xmzytest01.oss-cn-shenzhen.aliyuncs.com/batch/service/GHB_0000002_SERVICE_20210914.txt?Expires=1631616202&OSSAccessKeyId=LTAI4G7SGJ716ePb7uS9EXJM&Signature=Srv3mBMCHsDauuDVtJv7%2FpANscw%3D");
        log.info("urlencode:{}", urlencode);
        String url = Base64.decodeStr(urlencode);
        log.info("url:{}", url);

    }
}
