package com.xmzy.bank.ghb.cipher;

import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBRequestHeader;

/**
 * 华兴银行请求包装类
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
public class GHBRequestCipher extends AbstractGHBCipher<GHBRequest> {

    public GHBRequestCipher(String publicKey, String privateKey, String secretKey) {
        super(publicKey, privateKey, secretKey);
    }

    @Override
    protected String getEncodeBodySignStr(GHBRequest request, String encryptBodyBase64) {

        GHBRequestHeader header = request.getHeader();
        return encryptBodyBase64 + header.getRequestId() + header.getRequestTime();
    }

    @Override
    protected String getDecodeBodySignStr(GHBRequest request, String base64body) {

        GHBRequestHeader header = request.getHeader();
        return base64body + header.getRequestId() + header.getRequestTime();
    }
}
