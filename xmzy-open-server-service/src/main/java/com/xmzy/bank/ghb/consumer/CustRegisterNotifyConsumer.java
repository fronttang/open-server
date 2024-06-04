package com.xmzy.bank.ghb.consumer;

import java.util.Date;
import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.req.CustRegisterNotifyParam;
import com.xmzy.base.util.SnowFlakeUtil;
import com.xmzy.base.util.XmzyBeanUtils;
import com.xmzy.model.cust.entity.XmCustInfoExt;
import com.xmzy.model.cust.service.IXmCustInfoExtService;
import com.xmzy.mq.annotation.RetryMQ;
import com.xmzy.webt.api.req.WebtCustRegisterNotifyParam;

import cn.hutool.core.util.StrUtil;

/**
 * 客户注册成功通知处理，更新XmCustInfoExt并调用webt接口进行处理
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Component
public class CustRegisterNotifyConsumer {

    @Autowired
    private IXmCustInfoExtService custInfoExtService;

    @RetryMQ()
    @KafkaListener(topics = {GHBConstants.CUST_REGISTER_NOTIFY_TOPIC})
    public void onMessage(ConsumerRecord<String, CustRegisterNotifyParam> record) {

        CustRegisterNotifyParam notify = record.value();

        if (Objects.isNull(notify) || StrUtil.isBlank(notify.getUserId()) || StrUtil.isBlank(notify.getMerchantId())) {
            // 数据为空则直接完成
            return;
        }

        // 更新客户的开户状态 xm_cust_info_ext BANK_ACCOUNT_FLAG
        XmCustInfoExt custInfoExt = custInfoExtService.getOne(XmCustInfoExt::getCustNo, notify.getUserId());
        if (Objects.isNull(custInfoExt)) {
            custInfoExt = new XmCustInfoExt();
            custInfoExt.setSeqNo(SnowFlakeUtil.uniqueString());
            custInfoExt.setBankAccountFlag("0");
            custInfoExt.setFaceRecognitionFlag("0");
            custInfoExt.setCreDte(new Date());
        }
        custInfoExt.setCustNo(notify.getUserId());
        custInfoExt.setBankAccountFlag("1");
        custInfoExt.setUpdDte(new Date());

        LambdaQueryWrapper<XmCustInfoExt> wrapper = new LambdaQueryWrapper<XmCustInfoExt>();
        wrapper.eq(XmCustInfoExt::getCustNo, notify.getUserId());

        custInfoExtService.saveOrUpdate(custInfoExt, wrapper);

        // 这里调用webt的接口进行处理
        // 处理失败要有重试机制

        WebtCustRegisterNotifyParam param = new WebtCustRegisterNotifyParam();
        XmzyBeanUtils.copyProperties(notify, param);

        param.execute();
    }

}
