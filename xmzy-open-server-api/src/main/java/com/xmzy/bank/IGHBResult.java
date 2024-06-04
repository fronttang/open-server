package com.xmzy.bank;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.GHBResponseHeader;
import com.xmzy.base.IResult;
import com.xmzy.base.constant.ResultCodeConstants;

import cn.hutool.core.util.StrUtil;

/**
 * 华兴银行返回信息
 * 
 * @author fronttang
 * @date 2021/08/24
 */
public interface IGHBResult extends IResult {

    /**
     * 返回信息
     */
    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    default String getMsg() {
        return StrUtil.format("{}|{}", getErrorCode(), getErrorMsg());
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    default Integer getCode() {
        return ResultCodeConstants.BUSINESS_ERROR_CODE;
    }

    /**
     * 获取返回码
     * 
     * @return 返回码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    default String errorCode() {
        return getErrorCode();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    default String errorMsg() {
        return getErrorMsg();
    }

    /**
     * 获取返回码
     * 
     * @return 返回码
     */
    String getErrorCode();

    /**
     * 响应信息
     * 
     * @return
     */
    String getErrorMsg();

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    default boolean isSuccess() {
        return GHBResultCode.SUCCESS.equalsIgnoreCase(getErrorCode());
    }

    /**
     * 转成银行返回
     * 
     * @return
     */
    default GHBResponse toGHBResponse() {
        GHBResponse response = new GHBResponse();
        GHBResponseHeader header = new GHBResponseHeader();
        header.setErrorCode(this.getErrorCode());
        header.setErrorMsg(this.getErrorMsg());
        response.setHeader(header);
        return response;
    }

}
