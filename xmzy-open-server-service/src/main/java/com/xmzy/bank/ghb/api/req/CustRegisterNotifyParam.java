package com.xmzy.bank.ghb.api.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.xmzy.bank.ghb.IGHBRequestBody;

import lombok.Data;

/**
 * 客户注册成功请求报文
 */
@Data
public class CustRegisterNotifyParam implements IGHBRequestBody {

    private static final long serialVersionUID = 1L;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 银行客户编号
     */
    @NotEmpty
    @NotNull
    private String merchantId;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 客户状态
     */
    private String custStatus;

    /**
     * 客户ID
     */
    @NotEmpty
    @NotNull
    private String userId;

}
