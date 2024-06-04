package com.xmzy.bank.exception;

import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.constant.GHBResultCode;

import lombok.Getter;
import lombok.Setter;

/**
 * 银行返回异常
 * 
 * @author fronttang
 * @date 2021/08/24
 */
public class GHBBusinessException extends RuntimeException implements IGHBResult {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String errorCode = GHBResultCode.ERROR;

    public GHBBusinessException(IGHBResult result) {
        super(result.msg());
        this.errorCode = result.errorCode();
    }

    public GHBBusinessException(String code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    @Override
    public String getErrorMsg() {
        return this.getMessage();
    }

}
