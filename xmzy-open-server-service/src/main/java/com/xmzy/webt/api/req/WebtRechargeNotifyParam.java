package com.xmzy.webt.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.webt.IWebtRequestBody;
import com.xmzy.webt.api.WebtRechargeNotifyApi;
import com.xmzy.webt.api.resp.WebtNotifyVo;

import lombok.Data;

/**
 * 充值成功请求报文
 */
@Data
@ApiFactory(WebtRechargeNotifyApi.class)
public class WebtRechargeNotifyParam implements IWebtRequestBody, IExecutableApiRequest<WebtNotifyVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 银行订单编号
     */
    private String orderId;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 银行客户编号
     */
    private String merchantId;

    /**
     * 客户ID
     */
    private String userId;

    /**
     * 充值类型，1-可用余额充值(默认) 2-保证金充值
     */
    private String rechargeType;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 手续费,为合作平台收取的手续费，收款人收款金额=交易金额-手续费。
     */
    private String feeAmount;

    /**
     * 订单状态,0-成功
     */
    private String tranStatus;

}
