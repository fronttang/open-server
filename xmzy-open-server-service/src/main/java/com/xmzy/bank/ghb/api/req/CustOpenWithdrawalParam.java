package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.CustOpenWithdrawalApi;
import com.xmzy.bank.ghb.api.resp.CustOpenWithdrawalVo;

import lombok.Data;

/**
 * @Description:客户开放提现功能请求报文
 */
@Data
@ApiFactory(CustOpenWithdrawalApi.class)
public class CustOpenWithdrawalParam implements IGHBRequestBody, IExecutableApiRequest<CustOpenWithdrawalVo> {

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
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 是否允许提现 ALLOW-允许 NOALLOW-不允许
     */
    private String withdraw;

    /**
     * 银行客户编号
     */
    private String merchantId;
}
