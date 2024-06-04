package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 回盘文件通知应答报文
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReplyFileNotifyVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 银行标识,固定值：GHB
     */
    private String bankId = GHBConstants.BANK_ID;

    /**
     * 订单状态,0-成功,1-处理中,2-失败
     */
    private String tranStatus;

    /**
     * 银行订单编号
     */
    private String orderId;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;
}
