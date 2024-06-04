package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.PlatformAccountBalanceApi;
import com.xmzy.bank.ghb.api.resp.PlatformAccountBalanceVo;

import lombok.Data;

/**
 * 平台台账余额查询请求报文
 */
@Data
@ApiFactory(PlatformAccountBalanceApi.class)
public class PlatformAccountBalanceParam implements IGHBRequestBody, IExecutableApiRequest<PlatformAccountBalanceVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 证件类型
     */
    private String customerIdType;

    /**
     * 证件号码
     */
    private String customerIdNo;

}
