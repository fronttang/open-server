package com.xmzy.bank.ghb.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBRequestHeader;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.GHBResponseHeader;
import com.xmzy.model.xm.entity.XmGhbLog;
import com.xmzy.model.xm.service.IXmGhbLogService;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 华兴银行接口调用日志
 * 
 * @author fronttang
 * @date 2021/10/22
 */
@Slf4j
@Service
@DS("data")
public class GHBLogService {

    @Autowired
    private IXmGhbLogService ghbLogService;

    public boolean saveOrUpdate(GHBRequest request) {

        GHBRequestHeader header = request.getHeader();

        if (Objects.isNull(header) || StrUtil.isBlank(header.getRequestId())) {
            log.info("不正常GHBRequest:{}", JSON.toJSONString(request));
            return true;
        }

        XmGhbLog saveOrUpdate = new XmGhbLog();

        // 存在记录
        XmGhbLog exsits = ghbLogService.extendLambdaQuery().eq(XmGhbLog::getRequestId, header.getRequestId()).one();
        if (Objects.nonNull(exsits)) {
            saveOrUpdate.setSeqNo(exsits.getSeqNo());
        }

        Object reqBody = request.getBody();
        if (reqBody instanceof String) {
            reqBody = JSON.parse(Convert.toStr(reqBody));
        }

        saveOrUpdate.setRequestApi(request.getApi());
        saveOrUpdate.setReqBody(JSON.toJSONString(reqBody));
        saveOrUpdate.setReqHeader(JSON.toJSONString(header));
        saveOrUpdate.setRequestId(header.getRequestId());
        saveOrUpdate.setRequestTime(header.getRequestTime());

        LambdaQueryWrapper<XmGhbLog> wrapper = new LambdaQueryWrapper<XmGhbLog>();
        wrapper.eq(XmGhbLog::getRequestId, header.getRequestId());

        return ghbLogService.saveOrUpdate(saveOrUpdate, wrapper);
    }

    public boolean saveOrUpdate(GHBResponse response) {

        GHBResponseHeader header = response.getHeader();

        if (Objects.isNull(header) || StrUtil.isBlank(header.getRequestId())) {
            log.info("不正常GHBResponse:{}", JSON.toJSONString(response));
            return true;
        }

        XmGhbLog saveOrUpdate = new XmGhbLog();

        // 存在记录
        XmGhbLog exsits = ghbLogService.extendLambdaQuery().eq(XmGhbLog::getRequestId, header.getRequestId()).one();
        if (Objects.nonNull(exsits)) {
            saveOrUpdate.setSeqNo(exsits.getSeqNo());
        }

        Object respBody = response.getBody();
        if (respBody instanceof String) {
            respBody = JSON.parse(Convert.toStr(respBody));
        }

        saveOrUpdate.setRequestId(header.getRequestId());
        saveOrUpdate.setResponseId(header.getResponseId());
        saveOrUpdate.setResponseTime(header.getResponseTime());
        saveOrUpdate.setRespHeader(JSON.toJSONString(header));
        saveOrUpdate.setRespBody(JSON.toJSONString(respBody));
        saveOrUpdate.setCode(header.getErrorCode());
        saveOrUpdate.setMsg(header.getErrorMsg());

        LambdaQueryWrapper<XmGhbLog> wrapper = new LambdaQueryWrapper<XmGhbLog>();
        wrapper.eq(XmGhbLog::getRequestId, header.getRequestId());

        return ghbLogService.saveOrUpdate(saveOrUpdate, wrapper);
    }
}
