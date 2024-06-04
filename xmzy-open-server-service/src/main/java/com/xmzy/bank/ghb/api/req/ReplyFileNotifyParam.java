package com.xmzy.bank.ghb.api.req;

import com.xmzy.bank.ghb.IGHBRequestBody;

import lombok.Data;

/**
 * 回盘文件通知请求报文
 */
@Data
public class ReplyFileNotifyParam implements IGHBRequestBody {

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
     * 第三方批次号,需保证唯一性
     */
    private String thirdBatchNo;

    /**
     * 文件路径,文件全路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 代扣类型,SERVICE-服务费,TOLL-通行费,HISTORYTOLL-历史通行费,MARGIN-保证金,INVOICE-联合电服结算
     */
    private String withholdType;

    /**
     * 台账日期 YYYYMMdd
     */
    private String postingDate;
}
