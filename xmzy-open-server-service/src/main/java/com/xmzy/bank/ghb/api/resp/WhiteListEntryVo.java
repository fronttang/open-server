package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description:白名单录入应答报文
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WhiteListEntryVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 订单状态 S-成功；E-失败
     */
    private String reqStatus;
}
