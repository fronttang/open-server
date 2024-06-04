package com.xmzy.bank.constant;

/**
 * 银行返回码常量
 * 
 * @author fronttang
 * @date 2021/08/24
 */
public interface GHBResultCode {

    /**
     * 业务成功标志
     */
    String SUCCESS_REQ_CODE = "000000";

    /**
     * 成功标识
     */
    String SUCCESS = "0000";

    /**
     * 业务异常
     */
    String BUSINESS_ERROR = "1000";

    /**
     * 失败
     */
    String ERROR = "9999";

    /**
     * 密钥错误或失效
     */
    String SECRET_KEY_ERROR = "3001";

    /**
     * 解密失败，请检查密钥
     */
    String DECRYPT_ERROR = "3002";

    /**
     * 签名验证不过
     */
    String SIGN_DATA_ERROR = "3003";

}
