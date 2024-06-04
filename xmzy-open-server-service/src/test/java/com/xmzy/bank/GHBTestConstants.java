package com.xmzy.bank;

/**
 * GHB 银行常量
 * 
 * @author fronttang
 * @date 2021/08/26
 */
public interface GHBTestConstants {

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
     * 测试用加密KEY
     */
    String SECRET_KEY = "CF7A3D8AE62FA66E454ECA4AD9A6F2ED";

    /**
     * 银行公钥
     */
    String BANK_PUBLIC_KEY =
        "04C51D9176C84B6FE032115E6ABC62B5E7DC4384C70A0996F2F974258F4E50E1E1CD6504937FF48A3059355B99E2BCDC07362D08AC671B5FA84BE95B8A40DB0ED1";

    /**
     * 银行私钥
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
