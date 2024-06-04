package com.xmzy.bank.ghb.event;

import org.springframework.context.ApplicationEvent;

import com.xmzy.bank.ghb.GHBResponse;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 华兴银行返回事件通知
 * 
 * @author fronttang
 * @date 2021/10/22
 */
@Getter
public class GHBResponseEvent extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private GHBResponse response;

    /**
     * @param source
     */
    public GHBResponseEvent(GHBResponse source) {
        super(source);
        this.response = ObjectUtil.clone(source);
    }

}
