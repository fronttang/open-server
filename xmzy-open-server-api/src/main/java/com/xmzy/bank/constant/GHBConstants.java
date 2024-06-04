package com.xmzy.bank.constant;

/**
 * GHB 华兴银行常量
 * 
 * @author fronttang
 * @date 2021/08/26
 */
public interface GHBConstants {

    /**
     * 缓存在上下文的中请求关
     */
    String REQUEST_HEADER_CONTEXT_KEY = "REQUEST_HEADER";

    /**
     * 上下文中的secret key,为了防止请求的时候KEY是有效的，到了返回KEY已经失效了
     */
    String SECRET_CONTEXT_KEY = "SECRET_KEY";

    /**
     * 客户注册成功通知队列
     */
    String CUST_REGISTER_NOTIFY_TOPIC = "CUST_REGISTER_NOTIFY";

    /**
     * 平台结算结果通知队列
     */
    String PLATFORM_SETTLEMENT_NOTIFY_TOPIC = "PLATFORM_SETTLEMENT_NOTIFY";

    /**
     * 充值成功通知
     */
    String RECHARGE_NOTIFY_TOPIC = "RECHARGE_NOTIFY";

    /**
     * 回盘文件通知
     */
    String REPLY_FILE_NOTIFY_TOPIC = "REPLY_FILE_NOTIFY";

    /**
     * 应用编号，由合作方申请，银行生成。
     */
    String APP_ID = "10207023";

    /**
     * 银行标识
     */
    String BANK_ID = "GHB";

    /**
     * 银行分配的合作商编号
     */
    String PARENT_MERCHANT_ID = "518510003";

    /**
     * 银行公钥
     */
    String BANK_PUBLIC_KEY =
        "04C51D9176C84B6FE032115E6ABC62B5E7DC4384C70A0996F2F974258F4E50E1E1CD6504937FF48A3059355B99E2BCDC07362D08AC671B5FA84BE95B8A40DB0ED1";

    /**
     * 银行私钥（测试使用，生产中没有）
     */
    String BANK_PRIVATE_KEY = "00A6B8FFF085B935E5FBF4AA930F2A6BFCD79236CE3CBA9C5270039CA8D6A517E2";

    /**
     * 小马公钥
     */
    String XM_PUBLIC_KEY =
        "0454AD36645F6C49105FF36E2928B0788AADE3FC1EE2FB0FD9F50AE47FD2A05974B5E6659AC504F85ACA7EB8982CAEBEFFD88E15B48C17F900602DCFB5143E5112";

    /**
     * 小马私钥
     */
    String XM_PRIVATE_KEY = "00F314BFF367C9E6C0060B8AC9D1FD9DA83A3863ED456384CFAF399EC5FEB1DB6E";
}
