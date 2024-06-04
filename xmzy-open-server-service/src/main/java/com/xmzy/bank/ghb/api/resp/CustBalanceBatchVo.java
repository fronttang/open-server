package com.xmzy.bank.ghb.api.resp;

import java.util.List;

import com.xmzy.bank.ghb.GHBResponseBody;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description:客户台账余额批量查询应答报文
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustBalanceBatchVo extends GHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 银行标识
     */
    private String bankId;

    /**
     * 汇总条数
     */
    private String returnCount;

    /**
     * 每页条数
     */
    private String pageCount;

    /**
     * 查询页数
     */
    private String currentPage;

    /**
     * 客户余额列表
     */
    private List<CustBalanceList> custBalanceList;

    @Data
    public static class CustBalanceList {
        /**
         * 可用金额
         */
        private String availableBalance;

        /**
         * 实际金额
         */
        private String actualBalance;

        /**
         * 客户类型
         */
        private String customerType;

        /**
         * 银行客户编号
         */
        private String merchantId;

        /**
         * 客户名称
         */
        private String customerName;

        /**
         * 注册日期
         */
        private String openDate;

        /**
         * 账户状态 0-正常 3-冻结
         */
        private String accountStatus;

        /**
         * 子台账明细
         */
        private List<AccountList> accountList;

    }

    @Data
    public static class AccountList {

        /**
         * 子台账类型
         * 
         * IN_TRSIT-在途子户
         * 
         * MARGIN-保证金子户
         * 
         * WALLET-钱包子户
         */
        private String accountType;

        /**
         * 子台账可用金额
         */
        private String availableBalance;

        /**
         * 子台账实际金额
         */
        private String actualBalance;
    }
}
