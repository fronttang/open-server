package com.xmzy.bank.ghb.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.req.RechargeNotifyParam;
import com.xmzy.bank.ghb.enums.RechargeType;
import com.xmzy.base.util.XmzyBeanUtils;
import com.xmzy.model.cust.entity.XmCustInfoExt;
import com.xmzy.model.cust.entity.XmRechange;
import com.xmzy.model.cust.service.IXmCustInfoExtService;
import com.xmzy.model.cust.service.IXmReChangeService;
import com.xmzy.mq.annotation.RetryMQ;
import com.xmzy.webt.api.req.WebtRechargeNotifyParam;

import cn.hutool.core.util.StrUtil;

/**
 * 充值成功通知处理 调用webt的接口进行处理
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Component
public class RechargeNotifyConsumer {

    @Autowired
    private IXmCustInfoExtService custInfoExtService;

    @Autowired
    private IXmReChangeService reChangeService;

    @RetryMQ()
    @KafkaListener(topics = {GHBConstants.RECHARGE_NOTIFY_TOPIC})
    public void onMessage(ConsumerRecord<String, RechargeNotifyParam> record) {

        RechargeNotifyParam notify = record.value();

        if (Objects.isNull(notify) || StrUtil.isBlank(notify.getUserId()) || StrUtil.isBlank(notify.getMerchantId())) {
            // 数据为空则直接完成
            return;
        }

        if (RechargeType.MARGIN.code().equals(notify.getRechargeType())) {
            // 更新 xm_cust_info_ext
            XmCustInfoExt update = new XmCustInfoExt();
            update.setCustNo(notify.getUserId());
            update.setBankDepositFlag("1");
            update.setBankAccountFlag("1");
            update.setFaceRecognitionFlag("1");

            LambdaQueryWrapper<XmCustInfoExt> wrapper = new LambdaQueryWrapper<XmCustInfoExt>();
            wrapper.eq(XmCustInfoExt::getCustNo, notify.getUserId());

            custInfoExtService.saveOrUpdate(update, wrapper);
        }

        XmRechange rechange = null;

        // 幂等处理, orderId不能重复
        if (StrUtil.isNotBlank(notify.getOrderId())) {
            rechange = reChangeService.extendLambdaQuery().eq(XmRechange::getOrderId, notify.getOrderId()).one();
        }

        if (Objects.isNull(rechange)) {
            // 记录充值记录
            rechange = new XmRechange();
            rechange.setCustNo(notify.getUserId());
            rechange.setAmount(new BigDecimal(notify.getAmount()).divide(new BigDecimal(100)));
            rechange.setFeeAmount(new BigDecimal(notify.getFeeAmount()).divide(new BigDecimal(100)));
            rechange.setRechargeType(notify.getRechargeType());
            rechange.setReqSeqNo(notify.getReqSeqNo());
            rechange.setCreDte(new Date());
            rechange.setOrderId(notify.getOrderId());

            LambdaQueryWrapper<XmRechange> wrapper = new LambdaQueryWrapper<XmRechange>();
            wrapper.eq(XmRechange::getOrderId, notify.getOrderId());

            reChangeService.saveOrUpdate(rechange, wrapper);
        }

        // 调用webt的接口进行处理
        WebtRechargeNotifyParam param = new WebtRechargeNotifyParam();
        XmzyBeanUtils.copyProperties(notify, param);

        param.execute();
    }
}
