package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 平台结算结果应答
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlatformSettlementNotifyVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 银行标识
     */
    private String bankId;
}
