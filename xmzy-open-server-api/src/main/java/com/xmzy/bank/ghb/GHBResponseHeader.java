package com.xmzy.bank.ghb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.constant.GHBResultCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 银行返回报文header
 * 
 * @author fronttang
 * @date 2021/08/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GHBResponseHeader extends GHBHeader implements IGHBResult {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 响应流水，由银行生成，建议使用此流水查询交易及问题排查
     */
    private String responseId;

    /**
     * 响应时间，格式：yyyyMMddHHmmssSSS
     */
    private String responseTime;

    /**
     * 响应码
     */
    private String errorCode;

    /**
     * 响应信息
     */
    private String errorMsg;

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return GHBResultCode.SUCCESS.equals(getErrorCode());
    }
}
