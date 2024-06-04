package com.xmzy.bank.ghb.consumer;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.req.PlatformSettlementNotifyParam;
import com.xmzy.base.enums.EnumUtils;
import com.xmzy.base.sensitive.FastJsonSensitiveFilter;
import com.xmzy.model.base.enums.TollSettleSts;
import com.xmzy.model.bill.entity.XmTollSettleInfo;
import com.xmzy.model.bill.service.IXmTollSettleInfoService;
import com.xmzy.mq.annotation.RetryMQ;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 平台结算结果通知
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Slf4j
@Component
public class PlatformSettlementNotifyConsumer {

    @Autowired
    private IXmTollSettleInfoService tollSettleInfoService;

    @RetryMQ()
    @KafkaListener(topics = {GHBConstants.PLATFORM_SETTLEMENT_NOTIFY_TOPIC})
    public void onMessage(ConsumerRecord<String, PlatformSettlementNotifyParam> record) {

        PlatformSettlementNotifyParam notify = record.value();

        // 数据为空
        if (Objects.isNull(notify) || StrUtil.isBlank(notify.getReqSeqNo())) {
            return;
        }

        XmTollSettleInfo tollSettleInfo =
            tollSettleInfoService.getOne(XmTollSettleInfo::getSeqNo, notify.getReqSeqNo());

        if (Objects.isNull(tollSettleInfo)) {
            log.info("平台结算结果通知未知交易:{}", JSON.toJSONString(notify, new FastJsonSensitiveFilter()));
            return;
        }

        TollSettleSts sts = EnumUtils.init(TollSettleSts.class).fromCode(notify.getTranStatus());

        // 交易状态
        if (Objects.isNull(sts)) {
            log.info("平台结算结果通知未知交易状态:{}", JSON.toJSONString(notify, new FastJsonSensitiveFilter()));
            return;
        }
        BigDecimal amt = tollSettleInfo.getAmt().multiply(new BigDecimal(100));
        BigDecimal notifyAmt = new BigDecimal(notify.getAmount());

        if (amt.compareTo(notifyAmt) != 0) {
            log.info("平台结算结果通知金额不符:{}", JSON.toJSONString(notify, new FastJsonSensitiveFilter()));
            return;
        }

        XmTollSettleInfo update = new XmTollSettleInfo();
        update.setSeqNo(tollSettleInfo.getSeqNo());
        update.setSts(sts.code());
        update.setRemark(notify.getReqMsg());

        tollSettleInfoService.updateById(update);
    }
}
