package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型 :
 * 
 * WALLET-钱包
 * 
 * FEE-手续费
 * 
 * SERVICE-服务费
 * 
 * INTEREST-利息
 * 
 * COLLECTION-代收费用
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Getter
@AllArgsConstructor
public enum GHBBusinessType implements IEnum<GHBBusinessType> {

    /**
     * 钱包
     */
    WALLET("WALLET", "钱包"),

    /**
     * 手续费
     */
    FEE("FEE", "手续费"),

    /**
     * 服务费
     */
    SERVICE("SERVICE", "服务费"),

    /**
     * 利息
     */
    INTEREST("INTEREST", "利息"),

    /**
     * 代收费用
     */
    COLLECTION("COLLECTION", "代收费用"),

    ;

    private String code;

    private String name;
}
