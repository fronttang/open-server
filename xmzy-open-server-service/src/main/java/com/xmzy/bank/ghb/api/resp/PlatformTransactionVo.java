package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description:平台交易应答报文
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlatformTransactionVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 订单状态 0-成功 1-处理中 2-失败
     */
    private String tranStatus;
}
