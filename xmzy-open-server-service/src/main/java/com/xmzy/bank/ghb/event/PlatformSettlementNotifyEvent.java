package com.xmzy.bank.ghb.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.bank.ghb.api.req.PlatformSettlementNotifyParam;

import lombok.Getter;

/**
 * 平台结算结果通知队列
 * 
 * @author fronttang
 * @date 2021/08/30
 */
public class PlatformSettlementNotifyEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    private PlatformSettlementNotifyParam source;

    /**
     * @param source
     */
    public PlatformSettlementNotifyEvent(PlatformSettlementNotifyParam source) {
        super(source);
        this.source = source;
    }

}
