package com.xmzy.webt.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.webt.dto.BillNotifyDto;

import lombok.Getter;

/**
 * 扣费成功事件通知
 * 
 * @author fronttang
 * @date 2021/08/30
 */
public class BillNotifyEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    private BillNotifyDto source;

    /**
     * @param source
     */
    public BillNotifyEvent(BillNotifyDto source) {
        super(source);
        this.source = source;
    }

}
