package com.xmzy.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmzy.mq.retry.RetryMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fronttang
 * @date 2021/09/08
 */
@Slf4j
public class ObjectMapperTester {

    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        String json =
            "{\"times\":\"1\",\"topic\":\"BILL_NOTIFY\",\"type\":\"com.xmzy.mq.retry.RetryMessage\",\"timestamp\":\"1631082777126\",\"message\":{\"type\":\"1\",\"@class\":\"com.xmzy.webt.dto.BillNotifyDto\",\"custNo\":\"\\\"293026353338257408\\\"\",\"date\":\"1630404900000\",\"billNo\":\"\\\"293026353338257408\\\"\"}}";

        ObjectMapper mapper = new ObjectMapper();
        RetryMessage retryMessage = mapper.readValue(json, RetryMessage.class);
        log.info("retryMessage:{}", retryMessage.toString());
        log.info("retryMessage message:{}", retryMessage.getMessage());
    }
}
