package com.xmzy.bank.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户补缴保证金通知
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class CustDepositNotifyDto {

    /**
     * 客户号
     */
    @ApiModelProperty("客户号")
    private String custNo;

    /**
     * 客户在银行的客户编号
     */
    @ApiModelProperty("客户在银行的客户编号")
    private String merchantId;

    /**
     * 补缴金额
     */
    @ApiModelProperty("补缴金额")
    private BigDecimal amt;

    /**
     * 第三方流水号
     */
    @ApiModelProperty("流水号")
    private String reqSeqNo;
}
