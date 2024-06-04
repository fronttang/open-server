package com.xmzy.webt;

import com.xmzy.api.IApiRequest;

import lombok.Data;

/**
 * webt接口请求
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Data
public class WebtRequest<T extends IWebtRequestBody> implements IApiRequest {

    private static final long serialVersionUID = 1L;

    /**
     * sysHead
     */
    private WebtRequestSysHeader sysHead;

    /**
     * body
     */
    private T body;
}
