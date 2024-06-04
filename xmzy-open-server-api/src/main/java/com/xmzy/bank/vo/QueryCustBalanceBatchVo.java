package com.xmzy.bank.vo;

import java.util.List;

import com.xmzy.bank.ghb.IGHBResponseBody;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户台账余额批量查询应答报文
 */
@Data
public class QueryCustBalanceBatchVo implements IGHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 汇总条数
     */
    @ApiModelProperty("汇总条数")
    private String returnCount;

    /**
     * 每页条数
     */
    @ApiModelProperty("每页条数")
    private String pageCount;

    /**
     * 查询页数
     */
    @ApiModelProperty("查询页数")
    private String currentPage;

    /**
     * 客户余额列表
     */
    @ApiModelProperty("客户余额列表")
    private List<CustBalanceList> custBalanceList;

    @Data
    public static class CustBalanceList {

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
         * 客户类型
         */
        @ApiModelProperty("客户类型")
        private String customerType;

        /**
         * 银行客户编号
         */
        @ApiModelProperty("银行客户编号")
        private String merchantId;

        /**
         * 客户名称
         */
        @ApiModelProperty("客户名称")
        private String customerName;

        /**
         * 注册日期
         */
        @ApiModelProperty("注册日期")
        private String openDate;

        /**
         * 账户状态 0-正常 3-冻结
         */
        @ApiModelProperty("账户状态 0-正常 3-冻结")
        private String accountStatus;

        /**
         * 子台账明细
         */
        @ApiModelProperty("子台账明细")
        private List<CustAccountList> accountList;

    }

    @Data
    public static class CustAccountList {

        /**
         * 子台账类型
         * 
         * IN_TRSIT-在途子户
         * 
         * MARGIN-保证金子户
         * 
         * WALLET-钱包子户
         */
        @ApiModelProperty("子台账类型 IN_TRSIT-在途子户  MARGIN-保证金子户 WALLET-钱包子户")
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
