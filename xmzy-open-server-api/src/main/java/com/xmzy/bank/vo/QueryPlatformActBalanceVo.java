package com.xmzy.bank.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 平台台账余额查询应答报文
 */
@Data
public class QueryPlatformActBalanceVo {

    /**
     * 合作商编号
     */
    @ApiModelProperty("合作商编号")
    private String parentMerchantId;

    /**
     * 合作商名称
     */
    @ApiModelProperty("合作商名称")
    private String parentMerchantName;

    /**
     * 可用金额
     */
    @ApiModelProperty("可用金额")
    private String availableBalance;

    /**
     * 实际金额
     */
    @ApiModelProperty("实际金额")
    private String actualBalance;

    /**
     * 子台账明细
     */
    @ApiModelProperty("子台账明细")
    private List<AccountList> accountList;

    @Data
    public static class AccountList {

        /**
         * 子台账类型
         */
        @ApiModelProperty("子台账类型")
        private String accountType;

        /**
         * 子台账可用金额
         */
        @ApiModelProperty("子台账可用金额")
        private String availableBalance;

        /**
         * 子台账实际金额
         */
        @ApiModelProperty("子台账实际金额")
        private String actualBalance;
    }
}
