package com.xmzy.bank.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * webt 有关配置
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Data
@Configuration
@ConfigurationProperties(prefix = WebtProperties.PREFIX)
public class WebtProperties {

    public static final String PREFIX = "webt";

    /**
     * 接口地址
     */
    private String host = "http://ogrs.szxmzy.com/ogrs";
}
