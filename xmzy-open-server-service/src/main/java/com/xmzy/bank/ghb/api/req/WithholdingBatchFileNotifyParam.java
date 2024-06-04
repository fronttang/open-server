package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.resp.WithholdingBatchFileNotifyVo;

import com.xmzy.bank.ghb.api.WithholdingBatchFileNotifyApi;

import lombok.Data;

/**
 * @Description:代扣批量文件通知请求报文
 */
@Data
@ApiFactory(WithholdingBatchFileNotifyApi.class)
public class WithholdingBatchFileNotifyParam
    implements IGHBRequestBody, IExecutableApiRequest<WithholdingBatchFileNotifyVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    private String tradeNo;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 商户号
     */
    private String parentMerchantId;

    /**
     * 第三方批次号
     */
    private String thirdBatchNo;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 代扣类型 SERVICE-服务费 COLLECTION-通行费 WALLET-历史通行费 MARGIN-保证金INVOICE-联合电服结算清单
     */
    private String withholdType;

    /**
     * 台账日期
     */
    private String postingDate;

}
