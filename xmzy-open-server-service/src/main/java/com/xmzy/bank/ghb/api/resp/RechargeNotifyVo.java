package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.Data;

/**
 * 充值成功通知返回
 */
@Data
public class RechargeNotifyVo implements IGHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMessage;

}
