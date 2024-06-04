package com.xmzy.webt.api.resp;

import com.xmzy.webt.IWebtResponseBody;

import lombok.Data;

/**
 * 维恩贝特通知返回
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Data
public class WebtNotifyVo implements IWebtResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    private String success;
}
