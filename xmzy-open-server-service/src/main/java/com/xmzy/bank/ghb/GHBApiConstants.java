package com.xmzy.bank.ghb;

/**
 * 银行接口常量
 * 
 * @author fronttang
 * @date 2021/08/27
 */
public interface GHBApiConstants {

    /**
     * 加密密钥申请地址
     */
    String AES_KEY_GENERATE_PATH = "/api/security/crypto/aesKeyGenerator/V2";

    /**
     * 客户台账余额批量查询
     */
    String CUST_BALANCE_BATCH_PATH = "/api/custodian/its/customerAccount/v1";

    /**
     * 平台台账余额查询
     */
    String PLATFORM_ACCOUNT_BALANCE_PATH = "/api/custodian/its/platformAccount/v1";

    /**
     * 客户开放提现功能
     */
    String CUST_OPEN_WITHDRAWAL_PATH = "/api/custodian/its/openWithdrawal/v1";

    /**
     * 客户补缴保证金通知
     */
    String CUST_SUPPLEMENTARY_DEPOSIT_NOTIFY_PATH = "/api/custodian/its/supplementaryDeposit/v1";

    /**
     * 白名单录入
     */
    String WHITE_LIST_ADD_PATH = "/api/custodian/its/whiteList/v1";

    /**
     * 代扣批量文件通知
     */
    String WITHHOLDING_BATCH_FILE_NOTIFY_PATH = "/api/custodian/its/withhold/v1";

    /**
     * 平台交易接口地址
     */
    String PLATFORM_TRANSACTION_PATH = "/api/custodian/its/platformTransaction/v1";

    /**
     * 文件下载token申请地址
     */
    String GET_DOWNLOAD_FILE_TOKEN_PATH = "/api/custodian/its/applyBackToDiskFileToken/v1";

    /**
     * 获取对账文件token
     */
    String GET_RECONCILIATION_FILE_TOKEN_PATH = "/api/custodian/its/applyReconciliatFileToken/v1";

    /**
     * 文件下载地址
     */
    String DOWNLOAD_FILE_PATH = "/api/custodian/its/fileDownload/v1";
}
