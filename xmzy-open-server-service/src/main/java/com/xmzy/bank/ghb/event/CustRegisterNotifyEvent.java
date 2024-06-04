package com.xmzy.bank.ghb.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.bank.ghb.api.req.CustRegisterNotifyParam;

import lombok.Getter;

/**
 * 客户注册成功事件
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Getter
public class CustRegisterNotifyEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private CustRegisterNotifyParam source;

    public CustRegisterNotifyEvent(CustRegisterNotifyParam source) {
        super(source);
        this.source = source;
    }

}
