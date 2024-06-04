package com.xmzy.bank.ghb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.api.IApiRequest;

import lombok.Data;

/**
 * 银行接口入参
 * 
 * @author fronttang
 * @date 2021/08/24
 */
@Data
public class GHBRequest implements IApiRequest, IGHBMessage, Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求报文header
     */
    private GHBRequestHeader header;

    /**
     * 请求报文体
     */
    private Object body;

    /**
     * 请求接口地址
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String api;

}
