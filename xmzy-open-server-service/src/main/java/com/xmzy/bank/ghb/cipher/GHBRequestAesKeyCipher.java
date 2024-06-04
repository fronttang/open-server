package com.xmzy.bank.ghb.cipher;

import com.xmzy.bank.ghb.util.GHBSMUtils;

/**
 * 华兴银行请求加密密钥包装类
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
public class GHBRequestAesKeyCipher extends GHBRequestCipher {

    public GHBRequestAesKeyCipher(String publicKey, String privateKey) {
        super(publicKey, privateKey, null);
    }

    /**
     * 对body源数据进行加密并base64编码
     * 
     * @param bodyStr
     * @param secretKey
     * @return
     */
    @Override
    protected String encryptBody(String bodyStr) {
        // 申请密钥接口使用 SM2加密
        return GHBSMUtils.encryptUseSM2PublicKey(bodyStr, this.publicKey);
    }

    /**
     * 解密body
     * 
     * @param base64body
     * @return
     */
    @Override
    protected String decryptBody(String base64body) {
        // 申请密钥接口使用 SM2解密
        return GHBSMUtils.decryptUseSM2PrivateKey(base64body, this.privateKey);
    }

}
