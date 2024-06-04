package com.xmzy.bank.ghb.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.bank.ghb.GHBRequest;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 华兴银行请求事件
 * 
 * @author fronttang
 * @date 2021/10/22
 */
@Getter
public class GHBRequestEvent extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private GHBRequest request;

    /**
     * @param source
     */
    public GHBRequestEvent(GHBRequest source) {
        super(source);
        this.request = ObjectUtil.clone(source);
    }

}
