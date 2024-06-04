package com.xmzy.bank.ghb.api.resp;

import java.util.List;

import com.xmzy.bank.IGHBResult;
import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.Data;

/**
 * @Description: 平台台账余额查询应答报文
 */
@Data
public class PlatformAccountBalanceVo implements IGHBResponseBody, IGHBResult {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    private String errorMsg;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 银行标识
     */
    private String bankId;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 合作商名称
     */
    private String parentMerchantName;

    /**
     * 可用金额
     */
    private String availableBalance;

    /**
     * 实际金额
     */
    private String actualBalance;

    /**
     * 子台账明细
     */
    private List<AccountList> accountList;

    @Data
    private static class AccountList {

        /**
         * 子台账类型
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
