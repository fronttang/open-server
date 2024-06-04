package com.xmzy.bank.enums;

import com.xmzy.bank.constant.GHBResultCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 华兴银行错误码定义
 * 
 * @author fronttang
 * @date 2021/08/24
 */
@Getter
@AllArgsConstructor
public enum GHBResultEnum implements IGHBResultEnum {

    /**
     * 成功
     */
    SUCCESS(GHBResultCode.SUCCESS, "success"),

    /**
     * 失败
     */
    BUSINESS_ERROR(GHBResultCode.BUSINESS_ERROR, "error"),

    /**
     * 成功请求标志
     */
    REQ_SUCCESS(GHBResultCode.SUCCESS_REQ_CODE, "success"),

    /**
     * 密钥错误或失效
     */
    SECRET_KEY_ERROR(GHBResultCode.SECRET_KEY_ERROR, "密钥错误或失效"),

    /**
     * 解密失败,请检查密钥是否正确
     */
    DECRYPT_ERROR(GHBResultCode.DECRYPT_ERROR, "解密失败,请检查密钥是否正确"),

    /**
     * 签名验证不过
     */
    SIGN_DATA_ERROR(GHBResultCode.SIGN_DATA_ERROR, "签名验证不过"),

    ;

    private String errorCode;

    private String errorMsg;

}
