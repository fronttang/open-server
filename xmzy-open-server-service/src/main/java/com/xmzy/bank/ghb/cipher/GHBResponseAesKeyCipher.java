package com.xmzy.bank.ghb.cipher;

import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.util.GHBSMUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 申请加密密钥返回包装类
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@Slf4j
public class GHBResponseAesKeyCipher extends GHBResponseCipher {

    public GHBResponseAesKeyCipher(String publicKey, String privateKey) {
        super(publicKey, privateKey, null);
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

    /**
     * 对body源数据进行加密并base64编码
     * 
     * @param bodyStr
     * @param secretKey
     * @return
     */
    @Override
    protected String encryptBody(String bodyStr) {
        // 用密钥加密body
        return GHBSMUtils.encryptUseSM2PublicKey(bodyStr, this.publicKey);
    }

    /**
     * 密钥接口不需要验证body的SM3摘要
     */
    @Override
    protected String verifyReserve(String base64body, String reserve) {
        try {
            // 解密出来的body
            String bodyStr = decryptBody(base64body);
            return bodyStr;
        } catch (Exception e) {
            log.error("", e);
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        }
    }
}
