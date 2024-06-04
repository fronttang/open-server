package com.xmzy.bank.ghb.api;

import java.util.Date;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.api.req.DownloadFileTokenParam;
import com.xmzy.bank.ghb.api.resp.DownloadFileTokenVo;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.model.base.enums.FileType;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 文件下载token申请
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.GET_DOWNLOAD_FILE_TOKEN_PATH, name = "文件下载token申请")
public class GetDownloadFileTokenApi extends AbstractGHBApi<DownloadFileTokenParam, DownloadFileTokenVo> {

    @Override
    public DownloadFileTokenParam beforeExecute(DownloadFileTokenParam request) throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }
        if (StrUtil.isBlank(request.getReqSeqNo())) {
            request.setReqSeqNo(getReqSeqNo(request));
        }

        return super.beforeExecute(request);
    }

    @Override
    public DownloadFileTokenVo afterExecute(DownloadFileTokenParam request, DownloadFileTokenVo response)
        throws BusinessException {

        // 等于3才能进行下载
        if (!"3".equalsIgnoreCase(response.getTransactionStatus())) {
            throw new BusinessException("文件交易状态为不可下载");
        }

        return super.afterExecute(request, response);
    }

    public static DownloadFileTokenVo getToken(FileType fileType, Date fileDate, String thirdBatchNo) {

        DownloadFileTokenParam param = new DownloadFileTokenParam();
        param.setReqSeqNo(thirdBatchNo);
        param.setFileType(fileType.name());
        param.setFileDate(DateUtil.format(fileDate, DatePattern.PURE_DATE_PATTERN));
        // param.setThirdBatchNo(thirdBatchNo);

        return param.execute();
    }
}
