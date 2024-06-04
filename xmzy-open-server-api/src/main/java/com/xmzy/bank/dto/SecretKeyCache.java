package com.xmzy.bank.dto;

import java.util.Date;

import lombok.Data;

/**
 * 加密密钥缓存对象
 * 
 * @author fronttang
 * @date 2021/09/10
 */
@Data
public class SecretKeyCache {

    /**
     * 加密密钥
     */
    private String appSecret;

    /**
     * 申请时间
     */
    private Date applyTime;
}
