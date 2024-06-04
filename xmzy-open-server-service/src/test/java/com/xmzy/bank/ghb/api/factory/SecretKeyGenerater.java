package com.xmzy.bank.ghb.api.factory;

import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.api.SecretKeyGenerateApi;
import com.xmzy.bank.ghb.api.req.GetSecretKeyParam;
import com.xmzy.bank.ghb.cipher.GHBRequestAesKeyCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseAesKeyCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;

/**
 * 生成加密密钥
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@ApiFactory(exec = SecretKeyGenerateApi.class)
public class SecretKeyGenerater extends AbstractBankApiFactory<GetSecretKeyParam> {

    private SecretKeyGenerater(GHBRequest param, String publicKey, String privateKey) {
        super(param, publicKey, privateKey, null);
    }

    public static SecretKeyGenerater build(GHBRequest param, String publicKey, String privateKey) {
        return new SecretKeyGenerater(param, publicKey, privateKey);
    }

    @Override
    protected IGHBCipher<GHBRequest> getRequestCipher(GHBRequest request) {
        return new GHBRequestAesKeyCipher(publicKey, privateKey);
    }

    @Override
    protected IGHBCipher<GHBResponse> getResponseCipher(GHBResponse response) {
        return new GHBResponseAesKeyCipher(publicKey, privateKey);
    }
}
