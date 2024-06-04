package com.xmzy.bank.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台交易
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Data
public class PlatformTransactionDto {

    /**
     * 流水号
     */
    @ApiModelProperty("流水号")
    private String reqSeqNo;

    /**
     * 业务类型 : WALLET-钱包 FEE-手续费 SERVICE-服务费 INTEREST-利息 COLLECTION-代收费用
     */
    @ApiModelProperty("业务类型 : WALLET-钱包 FEE-手续费 SERVICE-服务费 INTEREST-利息 COLLECTION-代收费用")
    private String businessType;

    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额")
    private BigDecimal amount;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
