package com.xmzy.bank.ghb.api;

import java.util.Date;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.api.req.GetReconciliationFileTokenParam;
import com.xmzy.bank.ghb.api.resp.GetReconciliationFileTokenVo;
import com.xmzy.bank.ghb.enums.GHBReconciliationFileType;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 获取对账文件token
 * 
 * @author fronttang
 * @date 2021/09/13
 */
@GHBApi(path = GHBApiConstants.GET_RECONCILIATION_FILE_TOKEN_PATH, name = "获取对账文件token")
public class GetReconciliationFileTokenApi
    extends AbstractGHBApi<GetReconciliationFileTokenParam, GetReconciliationFileTokenVo> {

    @Override
    public GetReconciliationFileTokenParam beforeExecute(GetReconciliationFileTokenParam request)
        throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        if (StrUtil.isBlank(request.getReqSeqNo())) {
            request.setReqSeqNo(getReqSeqNo(request));
        }

        if (StrUtil.isBlank(request.getThirdBatchNo())) {
            request.setThirdBatchNo(getReqSeqNo(request));
        }

        return super.beforeExecute(request);
    }

    @Override
    public GetReconciliationFileTokenVo afterExecute(GetReconciliationFileTokenParam request,
        GetReconciliationFileTokenVo response) throws BusinessException {

        if (!response.isSuccess()) {
            throw new BusinessException(response);
        }

        // 等于3才能进行下载
        if (!"3".equalsIgnoreCase(response.getTransactionStatus())) {
            throw new BusinessException("文件交易状态为不可下载");
        }

        return super.afterExecute(request, response);
    }

    public static GetReconciliationFileTokenVo getToken(GHBReconciliationFileType fileType, Date fileDate) {

        GetReconciliationFileTokenParam param = new GetReconciliationFileTokenParam();
        param.setFileType(fileType.code());
        param.setFileDate(DateUtil.format(fileDate, DatePattern.PURE_DATE_PATTERN));

        return param.execute();
    }
}
