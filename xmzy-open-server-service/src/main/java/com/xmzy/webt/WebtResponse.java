package com.xmzy.webt;

import com.alibaba.fastjson.JSONObject;
import com.xmzy.api.IApiResponse;

import lombok.Data;

/**
 * webt 接口返回
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Data
public class WebtResponse implements IApiResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 返回 sys head
     */
    private WebtResponseSysHeader sysHead;

    /**
     * 返回body
     */
    private JSONObject body;
}
