package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台账户类型
 * 
 * WALLET-钱包子户
 * 
 * CUST_GL-客户总账
 * 
 * INTEREST-利息子户
 * 
 * SERVICE-服务费子户
 * 
 * FEE-手续费子户
 * 
 * COLLECTION-代收子户
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBPlatAccountType implements IEnum<GHBPlatAccountType> {

    /**
     * 钱包子户
     */
    WALLET("WALLET", "钱包子户"),

    /**
     * 客户总账
     */
    CUST_GL("CUST_GL", "客户总账"),

    /**
     * 利息子户
     */
    INTEREST("INTEREST", "利息子户"),

    /**
     * 服务费子户
     */
    SERVICE("SERVICE", "服务费子户"),

    /**
     * 手续费子户
     */
    FEE("FEE", "手续费子户"),

    /**
     * 代收子户
     */
    COLLECTION("COLLECTION", "代收子户"),

    ;

    private String code;

    private String name;
}
