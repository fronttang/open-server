package com.xmzy.bank.ghb.api.req;

import com.xmzy.bank.ghb.IGHBRequestBody;

import lombok.Data;

/**
 * 平台结算结果请求报文
 */
@Data
public class PlatformSettlementNotifyParam implements IGHBRequestBody {

    private static final long serialVersionUID = 1L;

    /**
     * 为该客户在银行的客户身份标识。
     */
    private String merchantId;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 业务流水号
     */
    private String reqSeqNo;

    /**
     * 业务类型，WALLET-钱包 FEE-手续费 SERVICE-服务费 INTEREST-利息 COLLECTION-代收费用
     */
    private String businessType;

    /**
     * 状态，1-成功，2-失败
     */
    private String tranStatus;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 响应信息
     */
    private String reqMsg;
}
