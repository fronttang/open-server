package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.Data;

/**
 * 客户注册成功应答报文
 */
@Data
public class CustRegisterNotifyVo implements IGHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码,000000 成功
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMessage;

}
