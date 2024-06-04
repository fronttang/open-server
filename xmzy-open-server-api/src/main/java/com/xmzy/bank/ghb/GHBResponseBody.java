package com.xmzy.bank.ghb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.constant.GHBResultCode;

import lombok.Data;

/**
 * 接口返回公共字段
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class GHBResponseBody implements IGHBResponseBody, IGHBResult {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private String reqCode;

    /**
     * 返回信息
     */
    private String reqMsg;

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorCode() {
        return this.getReqCode();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorMsg() {
        return this.getReqMsg();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return GHBResultCode.SUCCESS_REQ_CODE.equals(getErrorCode());
    }

}
