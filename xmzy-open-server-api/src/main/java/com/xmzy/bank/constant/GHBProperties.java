package com.xmzy.bank.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 华兴银行配置类
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = GHBProperties.PREFIX)
public class GHBProperties {

    public static final String PREFIX = "bank.ghb";

    /**
     * 银行公钥
     */
    private String publicKey = GHBConstants.BANK_PUBLIC_KEY;

    /**
     * 小马私钥
     */
    private String privateKey = GHBConstants.XM_PRIVATE_KEY;

    /**
     * 应用ID ，银行提供
     */
    private String appId = GHBConstants.APP_ID;

    /**
     * 银行分配的合作商编号
     */
    private String parentMerchantId = GHBConstants.PARENT_MERCHANT_ID;

    /**
     * 银行接口地址
     */
    private String host = "";

    /**
     * OSS桶名称
     */
    private String bucket = BankConstants.FILE_BUCKET_NAME;

}
