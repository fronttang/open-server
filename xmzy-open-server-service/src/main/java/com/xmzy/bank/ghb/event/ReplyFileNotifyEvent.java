package com.xmzy.bank.ghb.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.bank.ghb.api.req.ReplyFileNotifyParam;

import lombok.Getter;

/**
 * 回盘文件通知事件
 * 
 * @author fronttang
 * @date 2021/08/30
 */
public class ReplyFileNotifyEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    private ReplyFileNotifyParam source;

    /**
     * @param source
     */
    public ReplyFileNotifyEvent(ReplyFileNotifyParam source) {
        super(source);
        this.source = source;
    }

}
