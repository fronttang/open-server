package com.xmzy.bank.ghb.util;

import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * 银行加解密工具类
 * 
 * @author fronttang
 * @date 2021/08/26
 */
public final class GHBSMUtils {

    /**
     * 使用SM4加密数据
     * 
     * @param data
     * @param secretKey
     * @return
     */
    public static String encryptUseSM4(String data, String secretKey) {

        byte[] secretKeyHex = HexUtil.decodeHex(secretKey.toUpperCase());
        SymmetricCrypto sm4 = SmUtil.sm4(secretKeyHex);
        String encryptHex = sm4.encryptHex(data);

        return encryptHex.toUpperCase();
    }

    /**
     * 使用SM4解密数据
     * 
     * @param encryptDate
     * @param secretKey
     * @return
     */
    public static String decryptUseSM4(String encryptStr, String secretKey) {
        byte[] secretKeyHex = HexUtil.decodeHex(secretKey.toUpperCase());
        SymmetricCrypto sm4 = SmUtil.sm4(secretKeyHex);
        String decryptStr = sm4.decryptStr(encryptStr);

        return decryptStr;
    }

    /**
     * 解密数据流
     * 
     * @param encryptStream
     *            加密的输入流
     * @param decryptStream
     *            解密后的输出流
     * @param secretKey
     *            密钥
     */
    public static void decryptUseSM4(InputStream encryptStream, OutputStream decryptStream, String secretKey) {
        byte[] secretKeyHex = HexUtil.decodeHex(secretKey.toUpperCase());
        SymmetricCrypto sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, secretKeyHex, secretKeyHex);
        sm4.decrypt(encryptStream, decryptStream, true);
    }

    /**
     * 加密数据流
     * 
     * @param sourceStream
     *            要加密的输入流
     * @param decryptStream
     *            加密后的输出流
     * @param secretKey
     *            加密密钥
     */
    public static void encryptUseSM4(InputStream sourceStream, OutputStream encryptStream, String secretKey) {
        byte[] secretKeyHex = HexUtil.decodeHex(secretKey.toUpperCase());
        SymmetricCrypto sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, secretKeyHex, secretKeyHex);
        sm4.encrypt(sourceStream, encryptStream, true);
    }

    /**
     * 使用SM4解密base64加密后的数据
     * 
     * @param encryptDate
     * @param secretKey
     * @return
     */
    public static String decryptBase64UseSM4(String encryptBase64Str, String secretKey) {
        return decryptUseSM4(Base64.decodeStr(encryptBase64Str), secretKey);
    }

    /**
     * 使用SM4加密后base64
     * 
     * @param body
     * @param secretKey
     * @return
     */
    public static String encryptUseSM4Base64(String body, String secretKey) {
        return Base64.encode(encryptUseSM4(body, secretKey));
    }

    /**
     * 使用私钥对数据签名
     * 
     * @param signData
     * @param privateKey
     * @return
     */
    public static String sign(String signData, String privateKey) {

        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKey);
        // 创建sm2 对象
        SM2 sm2 = SmUtil.sm2(privateKeyParameters, null);

        byte[] signByte = sm2.sign(signData.getBytes(), null);
        signByte = SmUtil.rsAsn1ToPlain(signByte);

        return HexUtil.encodeHexStr(signByte).toUpperCase();
    }

    /**
     * 使用公钥验证数据签名
     * 
     * @param data
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean verify(String data, String sign, String publicKey) {
        String publicKeyHex = publicKey;
        // 这里需要根据公钥的长度进行加工
        if (publicKeyHex.length() == 130) {
            // 这里需要去掉开始第一个字节 第一个字节表示标记
            publicKeyHex = publicKeyHex.substring(2);
        }
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        // 创建sm2 对象
        SM2 sm2 = SmUtil.sm2(null, ecPublicKeyParameters);
        boolean verify = sm2.verify(data.getBytes(), SmUtil.rsPlainToAsn1(HexUtil.decodeHex(sign)));
        return verify;
    }

    /**
     * 使用公钥加密
     * 
     * @param data
     * @param publicKey
     * @return
     */
    public static String encryptUseSM2PublicKey(String data, String publicKey) {
        // 申请密钥接口使用 SM2加密
        String publicKeyHex = publicKey;
        // 这里需要根据公钥的长度进行加工
        if (publicKeyHex.length() == 130) {
            // 这里需要去掉开始第一个字节 第一个字节表示标记
            publicKeyHex = publicKeyHex.substring(2);
        }
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        // 创建sm2 对象
        SM2 sm2 = SmUtil.sm2(null, ecPublicKeyParameters);
        byte[] encrypt = sm2.encrypt(data, KeyType.PublicKey);
        return Base64.encode(encrypt);
    }

    /**
     * 使用私钥进行解密
     * 
     * @param Hexdata
     * @param privateKey
     * @return
     */
    public static String decryptUseSM2PrivateKey(String encryptStr, String privateKey) {

        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKey);
        // 创建sm2 对象
        SM2 sm2 = SmUtil.sm2(privateKeyParameters, null);

        String decrypt = sm2.decryptStr(encryptStr, KeyType.PrivateKey);

        return decrypt;
    }

}
