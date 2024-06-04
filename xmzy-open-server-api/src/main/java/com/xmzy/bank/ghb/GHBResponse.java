package com.xmzy.bank.ghb;

import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.api.IApiResponse;
import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.constant.GHBResultCode;

import lombok.Data;

/**
 * 银行接口返回
 * 
 * @author fronttang
 * @date 2021/08/24
 */
@Data
public class GHBResponse implements IApiResponse, IGHBResult, IGHBMessage, Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * 报文头
     */
    private GHBResponseHeader header;

    /**
     * 报文体
     */
    private Object body;

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorCode() {
        return Objects.nonNull(header) ? header.getErrorCode() : "";
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorMsg() {
        return Objects.nonNull(header) ? header.getErrorMsg() : "";
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return GHBResultCode.SUCCESS.equalsIgnoreCase(getErrorCode());
    }

}
