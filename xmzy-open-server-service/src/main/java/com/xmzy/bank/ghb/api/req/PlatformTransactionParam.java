package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.PlatformTransactionApi;
import com.xmzy.bank.ghb.api.resp.PlatformTransactionVo;

import lombok.Data;

/**
 * @Description:平台交易请求报文
 */
@Data
@ApiFactory(PlatformTransactionApi.class)
public class PlatformTransactionParam implements IGHBRequestBody, IExecutableApiRequest<PlatformTransactionVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    private String tradeNo;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 商户号
     */
    private String parentMerchantId;

    /**
     * 业务类型 : WALLET-钱包 FEE-手续费 SERVICE-服务费 INTEREST-利息 COLLECTION-代收费用
     */
    private String businessType;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 备注
     */
    private String remark;

}
