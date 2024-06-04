package com.xmzy.bank.controller;

import java.net.URL;
import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.xmzy.bank.api.IGHBBankApi;
import com.xmzy.bank.dto.AddWhiteListDto;
import com.xmzy.bank.dto.CustDepositNotifyDto;
import com.xmzy.bank.dto.DeleteWhiteListDto;
import com.xmzy.bank.dto.GetReconciliationFileDto;
import com.xmzy.bank.dto.PlatformTransactionDto;
import com.xmzy.bank.dto.QueryCustBalanceBatchDto;
import com.xmzy.bank.dto.WithholdingFileNotifyDto;
import com.xmzy.bank.ghb.api.CustBalanceBatchApi;
import com.xmzy.bank.ghb.api.CustOpenWithdrawalApi;
import com.xmzy.bank.ghb.api.CustSupplementaryDepositNotifyApi;
import com.xmzy.bank.ghb.api.DownloadFileApi;
import com.xmzy.bank.ghb.api.GetReconciliationFileTokenApi;
import com.xmzy.bank.ghb.api.PlatformAccountBalanceApi;
import com.xmzy.bank.ghb.api.PlatformTransactionApi;
import com.xmzy.bank.ghb.api.WhiteListEntryApi;
import com.xmzy.bank.ghb.api.WithholdingBatchFileNotifyApi;
import com.xmzy.bank.ghb.api.resp.CustBalanceBatchVo;
import com.xmzy.bank.ghb.api.resp.CustOpenWithdrawalVo;
import com.xmzy.bank.ghb.api.resp.CustSupplementaryDepositNotifyVo;
import com.xmzy.bank.ghb.api.resp.DownloadFileVo;
import com.xmzy.bank.ghb.api.resp.GetReconciliationFileTokenVo;
import com.xmzy.bank.ghb.api.resp.PlatformAccountBalanceVo;
import com.xmzy.bank.ghb.api.resp.PlatformTransactionVo;
import com.xmzy.bank.ghb.api.resp.WhiteListEntryVo;
import com.xmzy.bank.ghb.api.resp.WithholdingBatchFileNotifyVo;
import com.xmzy.bank.ghb.enums.GHBBusinessType;
import com.xmzy.bank.ghb.enums.GHBReconciliationFileType;
import com.xmzy.bank.vo.QueryCustBalanceBatchVo;
import com.xmzy.bank.vo.QueryPlatformActBalanceVo;
import com.xmzy.base.Result;
import com.xmzy.base.enums.EnumUtils;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.base.util.SnowFlakeUtil;
import com.xmzy.base.util.XmzyBeanUtils;
import com.xmzy.model.base.enums.FileType;
import com.xmzy.model.base.enums.ReturnFileStatus;
import com.xmzy.model.bill.entity.XmReturnFile;
import com.xmzy.model.bill.service.IXmReturnFileService;
import com.xmzy.util.api.IAliyunOSSApi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

/**
 * 华兴银行有关接口
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@RestController
public class GHBBankConstroller implements IGHBBankApi {

    @Autowired
    private IAliyunOSSApi ossApi;

    @Autowired
    private IXmReturnFileService returnFileService;

    @Override
    public Result<QueryPlatformActBalanceVo> queryPlatformAccountBalance() {

        PlatformAccountBalanceVo balanceVo = PlatformAccountBalanceApi.exec();

        if (Objects.nonNull(balanceVo)) {

            QueryPlatformActBalanceVo result = new QueryPlatformActBalanceVo();
            XmzyBeanUtils.copyProperties(balanceVo, result);

            return Result.SUCCESS(result);
        }

        return Result.ERROR();
    }

    @Override
    public Result<?> addWhiteList(@Valid AddWhiteListDto data) {

        WhiteListEntryVo whitelistVo = WhiteListEntryApi.addWhiteList(data);

        if (Objects.nonNull(whitelistVo)) {
            return Result.SUCCESS(whitelistVo);
        }

        return Result.ERROR();
    }

    @Override
    public Result<QueryCustBalanceBatchVo> queryCustBalanceBatch(@Valid QueryCustBalanceBatchDto data) {

        if (CollUtil.isEmpty(data.getActs())) {
            throw new BusinessException("银行客户编号不能为空");
        }

        CustBalanceBatchVo custBalanceVo =
            CustBalanceBatchApi.execute(data.getActs(), data.getPage(), data.getPageSize());

        QueryCustBalanceBatchVo result = new QueryCustBalanceBatchVo();
        XmzyBeanUtils.copyProperties(custBalanceVo, result);

        return Result.SUCCESS(result);
    }

    @Override
    public Result<?> editWhiteList(@Valid AddWhiteListDto data) {
        WhiteListEntryVo whitelistVo = WhiteListEntryApi.editWhiteList(data);

        if (Objects.nonNull(whitelistVo)) {
            return Result.SUCCESS(whitelistVo);
        }

        return Result.ERROR();
    }

    @Override
    public Result<?> deleteWhiteList(@Valid DeleteWhiteListDto data) {
        WhiteListEntryVo whitelistVo = WhiteListEntryApi.deleteWhiteList(data);

        if (Objects.nonNull(whitelistVo)) {
            return Result.SUCCESS(whitelistVo);
        }

        return Result.ERROR();
    }

    @Override
    public Result<?> custSupplementaryDepositNotify(@Valid CustDepositNotifyDto data) {

        CustSupplementaryDepositNotifyVo notifyVo =
            CustSupplementaryDepositNotifyApi.execute(data.getMerchantId(), data.getAmt(), data.getReqSeqNo());

        if (Objects.nonNull(notifyVo)) {
            return Result.SUCCESS(notifyVo);
        }

        return Result.ERROR();
    }

    @Override
    public Result<?> withholdingBatchFileNotify(@Valid WithholdingFileNotifyDto data) {

        FileType fileType = FileType.valueOf(data.getWithholdType());
        if (Objects.isNull(fileType)) {
            throw new BusinessException("不支持的代扣类型");
        }

        String filePath = data.getFilePath();
        if (StrUtil.isEmpty(filePath)) {
            throw new BusinessException("文件路径不能为空");
        }

        Result<String> tempUrl = ossApi.getTempUrl(filePath, 120);
        if (tempUrl != null && tempUrl.isSuccess() && StrUtil.isNotEmpty(tempUrl.getData())) {

            WithholdingBatchFileNotifyVo notifyVo = WithholdingBatchFileNotifyApi.execute(data.getPostingDate(),
                tempUrl.getData(), data.getThirdBatchNo(), fileType);
            if (Objects.nonNull(notifyVo)) {
                return Result.SUCCESS(notifyVo);
            }

            return Result.ERROR();
        }
        return tempUrl;
    }

    @Override
    public Result<?> platformTransaction(@Valid PlatformTransactionDto data) {

        GHBBusinessType businessType = GHBBusinessType.valueOf(data.getBusinessType());
        if (Objects.isNull(businessType)) {
            throw new BusinessException("不支持的交易类型");
        }

        PlatformTransactionVo result =
            PlatformTransactionApi.execute(businessType, data.getAmount(), data.getRemark(), data.getReqSeqNo());

        if (Objects.nonNull(result)) {
            return Result.SUCCESS(result);
        }

        return Result.ERROR();
    }

    @Override
    public Result<String> getReconciliationFile(@Valid GetReconciliationFileDto data) {

        GHBReconciliationFileType fileType =
            EnumUtils.init(GHBReconciliationFileType.class).fromCode(data.getFileType());

        if (Objects.isNull(fileType)) {
            throw new BusinessException("不支持的文件类型");
        }

        GetReconciliationFileTokenVo token = GetReconciliationFileTokenApi.getToken(fileType, data.getFileDate());

        if (Objects.isNull(token)) {
            throw new BusinessException("获取文件失败");
        }

        DownloadFileVo fileVo = DownloadFileApi.download(FileType.INENTCUS, token.getFileName(), token.getToken());

        if (Objects.nonNull(fileVo)) {

            if (GHBReconciliationFileType.REG_CUST == fileType) {

                XmReturnFile entity = new XmReturnFile();
                entity.setSeqNo(SnowFlakeUtil.uniqueString());
                entity.setBizType(FileType.INENTCUS.code());
                entity.setAcDte(new Date());
                entity.setTxnDte(new Date());
                entity.setTxnTime(new Date());
                entity.setStatus(ReturnFileStatus.ING.code());
                entity.setThirdBatchNo(token.getBatchId());

                URL url = URLUtil.url(fileVo.getUrl());
                String path = url.getPath();

                entity.setFileName(path);

                returnFileService.save(entity);
            }

            return Result.SUCCESS(fileVo.getUrl());
        }

        throw new BusinessException("获取文件失败");
    }

    @Override
    public Result<?> custOpenWithdrawal(String merchantId) {

        CustOpenWithdrawalVo custOpenWithdrawalVo = CustOpenWithdrawalApi.allow(merchantId);

        return Result.SUCCESS(custOpenWithdrawalVo);
    }

}
