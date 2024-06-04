package com.xmzy.webt.consumer;

import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.xmzy.mq.annotation.RetryMQ;
import com.xmzy.webt.WebtConstants;
import com.xmzy.webt.dto.BillNotifyDto;
import com.xmzy.webt.service.CustCoinService;

import lombok.extern.slf4j.Slf4j;

/**
 * 账单扣费成功通知处理
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Slf4j
@Component
public class BillNotifyConsumer {

    @Autowired
    private CustCoinService custCoinService;

    @RetryMQ()
    @KafkaListener(topics = {WebtConstants.BILL_NOTIFY_TOPIC})
    public void onMessage(ConsumerRecord<String, BillNotifyDto> record) {

        BillNotifyDto notify = record.value();

        if (Objects.isNull(notify)) {
            log.info("Kafka数据为空");
            return;
        }

        custCoinService.addCustCoin(notify);

    }

}
