package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.GetDownloadFileTokenApi;
import com.xmzy.bank.ghb.api.resp.DownloadFileTokenVo;

import lombok.Data;

/**
 * @Description:文件下载token申请请求报文
 */
@Data
@ApiFactory(GetDownloadFileTokenApi.class)
public class DownloadFileTokenParam implements IGHBRequestBody, IExecutableApiRequest<DownloadFileTokenVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 文件类型 SERVICE-服务费 COLLECTION-通行费 WALLET-历史通行费 MARGIN-保证金 INVOICE-联合电服结算清单 INENTCUS-增量客户注册信息文件
     */
    private String fileType;

    /**
     * 对账日期
     */
    private String fileDate;

    /**
     * 第三方批次号
     */
    private String thirdBatchNo;

    /**
     * 银行批次号
     */
    private String batchId;

}
