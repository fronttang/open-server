package com.xmzy.webt.api;

/**
 * webt 接口常量
 * 
 * @author fronttang
 * @date 2021/09/01
 */
public interface WebtApiConstants {

    /**
     * 成功返回码
     */
    String SUCCESS_CODE = "000000";

    /**
     * 开户成功通知 HOST
     */
    String OPEN_ACCOUNT_CALLBACK_HOST = "OpenAccountService";

    /**
     * 开户成功通知 PATH
     */
    String OPEN_ACCOUNT_CALLBACK_PATH = "open";

    /**
     * 充值成功通知 HOST
     */
    String ACCOUNT_RECHARGE_HOST = "AccountingService";

    /**
     * 充值成功通知 PATH
     */
    String ACCOUNT_RECHARGE_PATH = "accounting";
}
