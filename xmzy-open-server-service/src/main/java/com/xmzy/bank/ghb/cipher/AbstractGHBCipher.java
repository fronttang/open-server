package com.xmzy.bank.ghb.cipher;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBHeader;
import com.xmzy.bank.ghb.IGHBMessage;
import com.xmzy.bank.ghb.util.GHBSMUtils;
import com.xmzy.base.sensitive.FastJsonSensitiveFilter;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象银行参数加解密类
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@Slf4j
public abstract class AbstractGHBCipher<T extends IGHBMessage> implements IGHBCipher<T> {

    /**
     * 公钥
     */
    protected String publicKey;

    /**
     * 私钥
     */
    protected String privateKey;

    /**
     * 加密密钥
     */
    protected String secretKey;

    protected AbstractGHBCipher(String publicKey, String privateKey, String secretKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.secretKey = secretKey;
    }

    @Override
    public T decode(T cipher) throws GHBBusinessException {
        log.info("解密前数据:{}", JSON.toJSONString(cipher, new FastJsonSensitiveFilter()));

        GHBHeader header = cipher.getHeader();

        String base64body = String.valueOf(cipher.getBody());

        String signStr = getDecodeBodySignStr(cipher, base64body);
        // 签名数据base64
        String signDataBase64 = header.getSignData();
        // 验证签名
        if (!verify(signStr, signDataBase64, publicKey)) {
            throw new GHBBusinessException(GHBResultEnum.SIGN_DATA_ERROR);
        }

        String reserve = header.getReserve();
        String bodyStr = verifyReserve(base64body, reserve);
        cipher.setBody(bodyStr);

        log.info("解密后数据:{}", JSON.toJSONString(cipher, new FastJsonSensitiveFilter()));
        return cipher;
    }

    /**
     * @param cipher
     * @return
     */
    protected abstract String getDecodeBodySignStr(T cipher, String base64body);

    @Override
    public T encode(T source) throws GHBBusinessException {
        log.info("加密前数据:{}", JSON.toJSONString(source, new FastJsonSensitiveFilter()));

        GHBHeader header = source.getHeader();

        String bodyStr = JSON.toJSONString(source.getBody());
        // body摘要
        header.setReserve(getBodyReserve(bodyStr));

        // body加密
        String encryptBodyBase64 = encryptBody(bodyStr);
        source.setBody(encryptBodyBase64);

        // body签名
        String signStr = getEncodeBodySignStr(source, encryptBodyBase64);
        String signData = getBodySign(signStr, privateKey);
        header.setSignData(signData);

        log.info("加密后数据:{}", JSON.toJSONString(source, new FastJsonSensitiveFilter()));
        return source;
    }

    /**
     * @param source
     * @param encryptBodyBase64
     * @return
     */
    protected abstract String getEncodeBodySignStr(T source, String encryptBodyBase64);

    /**
     * body 源数据摘要
     * 
     * @param bodyStr
     * @return
     */
    protected String getBodyReserve(String bodyStr) {
        // 对BODY源数据进行SM3摘要
        String reserve = SmUtil.sm3(bodyStr).toUpperCase();
        return reserve;
    }

    /**
     * 对数据进行签名
     * 
     * @param header
     * @param encryptBodyBase64
     * @return
     */
    protected String getBodySign(String signStr, String privateKey) {

        // 对签名源数据进行SM3摘要
        String digestSignStr = SmUtil.sm3(signStr).toUpperCase();

        // 用私钥生成签名
        String privateKeyHex = privateKey;
        String sign = GHBSMUtils.sign(digestSignStr, privateKeyHex);

        // base64编码
        String signDataBase64 = Base64.encode(sign);
        return signDataBase64;
    }

    /**
     * 对body源数据进行加密并base64编码
     * 
     * @param bodyStr
     * @param secretKey
     * @return
     */
    protected String encryptBody(String bodyStr) {
        // 用密钥加密body
        return GHBSMUtils.encryptUseSM4Base64(bodyStr, this.secretKey);
    }

    /**
     * 解密body
     * 
     * @param base64body
     * @return
     */
    protected String decryptBody(String base64body) {
        try {
            // 用密钥解密body
            return GHBSMUtils.decryptBase64UseSM4(base64body, this.secretKey);
        } catch (Exception e) {
            throw new GHBBusinessException(GHBResultEnum.DECRYPT_ERROR);
        }
    }

    /**
     * 验证摘要
     * 
     * @param base64body
     * @param reserve
     * @return
     */
    protected String verifyReserve(String base64body, String reserve) {
        try {
            // 解密出来的body
            String bodyStr = decryptBody(base64body);

            String bodyreserve = getBodyReserve(bodyStr);

            if (!reserve.equalsIgnoreCase(bodyreserve)) {
                throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
            }
            return bodyStr;
        } catch (GHBBusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        }
    }

    /**
     * 验证签名
     * 
     * @param signStr
     * @param signDataBase64
     * @param publicKey
     * @return
     */
    protected boolean verify(String signStr, String signDataBase64, String publicKey) {
        // 签名数据摘要
        String digestSignStr = SmUtil.sm3(signStr).toUpperCase();

        // 签名数据base64解码
        String signData = Base64.decodeStr(signDataBase64);
        String publicKeyHex = publicKey;
        boolean verify = GHBSMUtils.verify(digestSignStr, signData, publicKeyHex);
        return verify;
    }
}
