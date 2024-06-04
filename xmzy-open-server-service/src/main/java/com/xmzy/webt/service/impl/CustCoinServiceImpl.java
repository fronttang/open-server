package com.xmzy.webt.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xmzy.base.enums.EnumUtils;
import com.xmzy.base.util.SnowFlakeUtil;
import com.xmzy.model.base.enums.CoinRecordType;
import com.xmzy.model.base.enums.CoinSourceType;
import com.xmzy.model.base.enums.CoinStatus;
import com.xmzy.model.base.enums.CoinType;
import com.xmzy.model.coin.entity.XmCustCoin;
import com.xmzy.model.coin.entity.XmCustCoinRecord;
import com.xmzy.model.coin.entity.XmCustCoinUsable;
import com.xmzy.model.coin.service.IXmCustCoinRecordService;
import com.xmzy.model.coin.service.IXmCustCoinService;
import com.xmzy.model.coin.service.IXmCustCoinUsableService;
import com.xmzy.webt.dto.BillNotifyDto;
import com.xmzy.webt.service.CustCoinService;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

/**
 * 客户金币service
 * 
 * 1. 用户每结清一笔通行费账单，及已支付完成的通行费账单，<br/>
 * 每1000元（本金）积累一枚金币，小数点采用去尾数取整 <br/>
 * 
 * 2. 用户每结清一笔平台服务费，及已支付完成的日/周结服务费账单；<br/>
 * 每在平台产生一笔额外平台服务消费，及支付使用升级版车队管理功能等，每1元积累一枚金币，小数点采用去尾数取整<br/>
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Service
public class CustCoinServiceImpl implements CustCoinService {

    @Autowired
    private IXmCustCoinService xmCustCoinService;

    @Autowired
    private IXmCustCoinRecordService custCoinRecordService;

    @Autowired
    private IXmCustCoinUsableService custCoinUsableService;

    @Override
    @Transactional
    public boolean addCustCoin(BillNotifyDto param) {

        XmCustCoinRecord xmCustCoinRecord = custCoinRecordService.extendLambdaQuery()
            .eq(XmCustCoinRecord::getCustNo, param.getCustNo()).eq(XmCustCoinRecord::getSourceType, param.getType())
            .eq(XmCustCoinRecord::getSourceNo, param.getBillNo()).one();

        if (Objects.nonNull(xmCustCoinRecord)) {
            // 已经存在
            return true;
        }

        DateTime expireTime = DateUtil.offsetDay(new Date(), 30);
        String recordNo = SnowFlakeUtil.uniqueString();
        BigDecimal quantity = getCoinAmt(param.getAmt(), param.getType());
        String custNo = param.getCustNo();

        // 数量为0则不记录
        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }

        // 添加金币记录表
        XmCustCoinRecord record = addCustCoinRecord(param, expireTime, recordNo, quantity, custNo);

        // 添加可用金币记录表
        addCustCoinUsable(recordNo, custNo, record);

        // 更新客户金币记录表
        updateCustCoin(quantity, custNo);
        return true;
    }

    private BigDecimal getCoinAmt(BigDecimal billAmt, String type) {

        BigDecimal amt = BigDecimal.ZERO;

        // 通行费
        if (CoinSourceType.TOLL.code().equals(type)) {
            // 1000 积一分
            amt = billAmt.divide(new BigDecimal(1000D));
        } else if (CoinSourceType.SERVICE_FEE.code().equals(type)) {
            // 1元积一分
            amt = billAmt;
        }
        amt.setScale(0, RoundingMode.DOWN);
        return new BigDecimal(amt.longValue());
    }

    private XmCustCoinRecord addCustCoinRecord(BillNotifyDto param, DateTime expireTime, String recordNo,
        BigDecimal quantity, String custNo) {
        XmCustCoinRecord record = new XmCustCoinRecord();
        record.setSeqNo(recordNo);
        record.setCustNo(custNo);

        // TODO
        record.setQuantity(quantity);
        record.setExpireTime(expireTime.toJdkDate());

        String title = "账单完成";
        CoinSourceType sourceType = EnumUtils.init(CoinSourceType.class).fromCode(param.getType());
        if (Objects.nonNull(sourceType)) {
            title = sourceType.desc();
        }
        record.setTitle(title);
        record.setType(CoinRecordType.IN.code());

        // 来源
        record.setSourceAmt(param.getAmt());
        record.setSourceNo(param.getBillNo());
        record.setSourceType(param.getType());

        custCoinRecordService.save(record);
        return record;
    }

    private void addCustCoinUsable(String recordNo, String custNo, XmCustCoinRecord record) {
        XmCustCoinUsable usable = new XmCustCoinUsable();
        usable.setCustNo(custNo);
        usable.setSeqNo(SnowFlakeUtil.uniqueString());
        usable.setExpireTime(record.getExpireTime());
        usable.setRecordNo(recordNo);
        usable.setQuantity(record.getQuantity());
        usable.setStatus(CoinStatus.NORMAL.code());
        custCoinUsableService.save(usable);
    }

    private void updateCustCoin(BigDecimal quantity, String custNo) {

        XmCustCoin custCoin = xmCustCoinService.lambdaQuery().eq(XmCustCoin::getCustNo, custNo).one();
        if (Objects.isNull(custCoin)) {
            custCoin = new XmCustCoin();
            custCoin.setCustNo(custNo);
            custCoin.setType(CoinType.GOLD.code());
            custCoin.setTotal(BigDecimal.ZERO);
            custCoin.setCharge(BigDecimal.ZERO);
            custCoin.setFreeze(BigDecimal.ZERO);
        }

        // 总金币
        BigDecimal total = custCoin.getTotal().add(quantity);
        if (total.compareTo(BigDecimal.ZERO) == -1) {
            custCoin.setTotal(BigDecimal.ZERO);
        } else {
            custCoin.setTotal(total);
        }

        // 可用金币
        BigDecimal charge = custCoin.getCharge().add(quantity);
        if (charge.compareTo(BigDecimal.ZERO) == -1) {
            custCoin.setCharge(BigDecimal.ZERO);
        } else {
            custCoin.setCharge(charge);
        }

        xmCustCoinService.saveOrUpdate(custCoin);
    }

}
