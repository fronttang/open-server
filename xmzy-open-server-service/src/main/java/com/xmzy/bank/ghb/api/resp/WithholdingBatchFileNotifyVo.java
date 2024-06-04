package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description:代扣批量文件通知应答报文
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WithholdingBatchFileNotifyVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 0-成功 1-处理中 2-失败
     */
    private String reqStatus;
}
