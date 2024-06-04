package com.xmzy.bank.ghb.api.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.Data;

/**
 * 获取对账文件token
 * 
 * @author fronttang
 * @date 2021/09/13
 */
@Data
public class GetReconciliationFileTokenVo implements IGHBResponseBody, IGHBResult {

    private static final long serialVersionUID = 1L;

    private String transCode;

    private String batchId;

    private String bankId;

    private String returnCode;

    private String returnMessage;

    /**
     * 业务流水号 合作平台业务流水号
     */
    private String bizNo;

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
     * 文件日期 YYYYMMDD
     */
    private String fileDate;

    /**
     * 交易状态 1、文件不存在 2、文件处理中 3、文件下载成功
     */
    private String transactionStatus;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件下载接口使用的token
     */
    private String token;

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorCode() {
        return this.getReturnCode();
    }

    @Override
    public String getErrorMsg() {
        return this.getReturnMessage();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return GHBResultCode.SUCCESS_REQ_CODE.equals(getReturnCode());
    }
}
