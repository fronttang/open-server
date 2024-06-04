package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.CustSupplementaryDepositNotifyApi;
import com.xmzy.bank.ghb.api.resp.CustSupplementaryDepositNotifyVo;

import lombok.Data;

/**
 * @Description:客户补缴保证金通知请求报文
 */
@Data
@ApiFactory(CustSupplementaryDepositNotifyApi.class)
public class CustSupplementaryDepositNotifyParam
    implements IGHBRequestBody, IExecutableApiRequest<CustSupplementaryDepositNotifyVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 银行客户编号
     */
    private String merchantId;

    /**
     * 保证金补缴金额
     */
    private String marginAmt;
}
