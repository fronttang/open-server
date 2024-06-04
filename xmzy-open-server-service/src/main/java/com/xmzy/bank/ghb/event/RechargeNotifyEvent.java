package com.xmzy.bank.ghb.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.bank.ghb.api.req.RechargeNotifyParam;

import lombok.Getter;

/**
 * 充值成功事件
 * 
 * @author fronttang
 * @date 2021/08/30
 */
public class RechargeNotifyEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    private RechargeNotifyParam source;

    /**
     * @param source
     */
    public RechargeNotifyEvent(RechargeNotifyParam source) {
        super(source);
        this.source = source;
    }

}
