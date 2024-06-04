package com.xmzy.bank.ghb.api;

import java.util.Date;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.api.req.WithholdingBatchFileNotifyParam;
import com.xmzy.bank.ghb.api.resp.WithholdingBatchFileNotifyVo;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.model.base.enums.FileType;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 代扣批量文件通知
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.WITHHOLDING_BATCH_FILE_NOTIFY_PATH, name = "代扣批量文件通知")
public class WithholdingBatchFileNotifyApi
    extends AbstractReqResponseApi<WithholdingBatchFileNotifyParam, WithholdingBatchFileNotifyVo> {

    @Override
    public WithholdingBatchFileNotifyParam beforeExecute(WithholdingBatchFileNotifyParam request)
        throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        if (StrUtil.isBlank(request.getReqSeqNo())) {
            request.setReqSeqNo(getReqSeqNo(request));
        }

        if (StrUtil.isBlank(request.getTradeNo())) {
            request.setTradeNo(getReqSeqNo(request));
        }

        return super.beforeExecute(request);
    }

    @Override
    public WithholdingBatchFileNotifyVo afterExecute(WithholdingBatchFileNotifyParam request,
        WithholdingBatchFileNotifyVo response) throws BusinessException {

        super.afterExecute(request, response);

        if ("2".equalsIgnoreCase(response.getReqStatus())) {
            throw new BusinessException(response);
        }

        return response;
    }

    public static WithholdingBatchFileNotifyVo execute(Date date, String filePath, String batchNo, FileType fileType) {

        WithholdingBatchFileNotifyParam param = new WithholdingBatchFileNotifyParam();
        param.setPostingDate(DateUtil.format(date, DatePattern.PURE_DATE_PATTERN));
        param.setFilePath(filePath);
        param.setThirdBatchNo(batchNo);
        param.setWithholdType(fileType.name());

        return param.execute();
    }
}
