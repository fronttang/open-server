package com.xmzy.bank.ghb.consumer;

import java.net.URL;
import java.util.Date;
import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.ghb.api.DownloadFileApi;
import com.xmzy.bank.ghb.api.GetDownloadFileTokenApi;
import com.xmzy.bank.ghb.api.req.ReplyFileNotifyParam;
import com.xmzy.bank.ghb.api.resp.DownloadFileTokenVo;
import com.xmzy.bank.ghb.api.resp.DownloadFileVo;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.base.sensitive.FastJsonSensitiveFilter;
import com.xmzy.base.util.SnowFlakeUtil;
import com.xmzy.model.base.enums.FileType;
import com.xmzy.model.base.enums.ReturnFileStatus;
import com.xmzy.model.bill.entity.XmReturnFile;
import com.xmzy.model.bill.service.IXmReturnFileService;
import com.xmzy.mq.annotation.RetryMQ;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 回盘文件通知处理， 写入xm_return_file 表
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Slf4j
@Component
public class ReplyFileNotifyConsumer {

    @Autowired
    private IXmReturnFileService returnFileService;

    @RetryMQ()
    @KafkaListener(topics = {GHBConstants.REPLY_FILE_NOTIFY_TOPIC})
    public void onMessage(ConsumerRecord<String, ReplyFileNotifyParam> record) {

        ReplyFileNotifyParam notify = record.value();

        if (Objects.isNull(notify)) {
            log.error("数据为空");
            return;
        }

        XmReturnFile entity = new XmReturnFile();
        entity.setSeqNo(SnowFlakeUtil.uniqueString());

        FileType fileType = FileType.valueOf(notify.getWithholdType());
        if (Objects.isNull(fileType)) {
            log.error("不支持的文件类型");
            return;
        }

        String postingDate = notify.getPostingDate();
        DateTime acDte = DateUtil.parse(postingDate);

        entity.setBizType(fileType.code());

        // TODO
        entity.setAcDte(new Date());
        entity.setTxnDte(new Date());
        entity.setTxnTime(new Date());

        entity.setStatus(ReturnFileStatus.ING.code());
        entity.setThirdBatchNo(notify.getReqSeqNo());

        // 去银行获取文件token
        DownloadFileTokenVo fileTokenVo = GetDownloadFileTokenApi.getToken(fileType, acDte, notify.getReqSeqNo());

        if (Objects.isNull(fileTokenVo) || StrUtil.isBlank(fileTokenVo.getToken())) {
            log.error("重新处理数据:{}", JSON.toJSONString(notify, new FastJsonSensitiveFilter()));
            throw new BusinessException("获取文件token失败");
        }

        // 下载文件（这里会上传到OSS）
        DownloadFileVo fileVo = DownloadFileApi.download(fileType, fileTokenVo.getFileName(), fileTokenVo.getToken());

        if (Objects.isNull(fileVo) || StrUtil.isBlank(fileVo.getUrl())) {
            log.error("重新处理数据:{}", JSON.toJSONString(notify, new FastJsonSensitiveFilter()));
            throw new BusinessException("文件下载失败");
        }

        URL url = URLUtil.url(fileVo.getUrl());
        String path = url.getPath();
        entity.setFileName(path);

        returnFileService.save(entity);

    }
}
