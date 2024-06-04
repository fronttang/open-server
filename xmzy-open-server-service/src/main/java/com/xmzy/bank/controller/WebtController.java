package com.xmzy.bank.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmzy.base.Result;
import com.xmzy.mq.push.dto.PushMessageData;
import com.xmzy.mq.push.dto.PushQueueData;
import com.xmzy.mq.push.util.PushQueueUtils;
import com.xmzy.mq.util.KafkaUtils;
import com.xmzy.webt.WebtConstants;
import com.xmzy.webt.dto.BillNotifyDto;
import com.xmzy.webt.event.BillNotifyEvent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 提供给维恩贝特的接口
 * 
 * 1. 推送通知
 * 
 * 2. 服务费扣款成功通知
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@RestController
@RequestMapping("/webt")
@Api(tags = "维恩贝特接口")
public class WebtController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    /**
     * 推送通知（直接写入队列）
     * 
     * @return
     */
    @PostMapping("/notify/push")
    @ApiOperation(tags = "维恩贝特接口", value = "消息推送")
    public Result<?> pushNotify(@RequestBody PushQueueData data) {

        PushMessageData message = data.getMessage();
        message.setSummary(message.getContent());

        PushQueueUtils.addPushData(data);

        return Result.SUCCESS();
    }

    /**
     * 账单扣款成功通知(异步处理)
     * 
     * @param serviceFee
     * @return
     */
    @PostMapping("/notify/bill")
    @ApiOperation(tags = "维恩贝特接口", value = "账单扣款成功通知")
    public Result<?> billNotify(@RequestBody BillNotifyDto notify) {

        // 发布事件通知，用于记录日志等操作
        publisher.publishEvent(new BillNotifyEvent(notify));

        KafkaUtils.sendMessage(WebtConstants.BILL_NOTIFY_TOPIC, notify);

        return Result.SUCCESS();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }
}
