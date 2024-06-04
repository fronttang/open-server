package com.xmzy.bank.ghb.api.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:客户补缴保证金通知应答报文
 */
@Data
@ToString(callSuper = true)
public class CustSupplementaryDepositNotifyVo implements IGHBResponseBody, IGHBResult {

    private static final long serialVersionUID = 1L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 银行标识
     */
    private String bankId;

    /**
     * 返回码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMessage;

    private String errorMsg;

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorCode() {
        return this.getReturnCode();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return GHBResultCode.SUCCESS_REQ_CODE.equals(getReturnCode());
    }
}
