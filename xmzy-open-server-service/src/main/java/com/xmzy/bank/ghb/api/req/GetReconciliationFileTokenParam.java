package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.GetReconciliationFileTokenApi;
import com.xmzy.bank.ghb.api.resp.GetReconciliationFileTokenVo;

import lombok.Data;

/**
 * 获取对账文件token
 * 
 * @author fronttang
 * @date 2021/09/13
 */
@Data
@ApiFactory(GetReconciliationFileTokenApi.class)
public class GetReconciliationFileTokenParam
    implements IGHBRequestBody, IExecutableApiRequest<GetReconciliationFileTokenVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方流水
     */
    private String reqSeqNo;

    /**
     * 银行分配的合作商编号
     */
    private String parentMerchantId;

    /**
     * 文件类型
     * 
     * 2：客户对账文件（只取对账日期的当天新增客户信息）
     * 
     * 3：交易对账文件
     * 
     * 4：余额对账文件
     * 
     * 5：批量分账
     * 
     * 6：增量客户注册信息文件
     */
    private String fileType;

    /**
     * 对账日期 YYYYMMDD，只支持最近30天内
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
