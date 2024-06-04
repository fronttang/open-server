package com.xmzy.bank.ghb.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.event.GHBRequestEvent;
import com.xmzy.bank.ghb.event.GHBResponseEvent;
import com.xmzy.bank.ghb.service.GHBLogService;

/**
 * 华兴银行接口调用日志
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Component
public class GHBLogHandler {

    @Autowired
    private GHBLogService logService;

    @Async
    @EventListener(value = GHBRequestEvent.class)
    public void ghbRequest(GHBRequestEvent event) {
        GHBRequest request = event.getRequest();

        logService.saveOrUpdate(request);
    }

    @Async
    @EventListener(value = GHBResponseEvent.class)
    public void ghbRequest(GHBResponseEvent event) {
        GHBResponse response = event.getResponse();

        logService.saveOrUpdate(response);
    }
}
