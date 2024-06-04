package com.xmzy.bank.ghb.cipher;

import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.GHBResponseHeader;

/**
 * 银行返回加解密类
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
public class GHBResponseCipher extends AbstractGHBCipher<GHBResponse> {

    public GHBResponseCipher(String publicKey, String privateKey, String secretKey) {
        super(publicKey, privateKey, secretKey);
    }

    @Override
    protected String getDecodeBodySignStr(GHBResponse response, String base64body) {

        GHBResponseHeader header = response.getHeader();
        return base64body + header.getRequestId() + header.getResponseId();
    }

    @Override
    protected String getEncodeBodySignStr(GHBResponse response, String encryptBodyBase64) {

        GHBResponseHeader header = response.getHeader();
        return encryptBodyBase64 + header.getRequestId() + header.getResponseId();
    }
}
