package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户账户类型
 * 
 * IN_TRSIT-在途子户
 * 
 * MARGIN-保证金子户
 * 
 * WALLET-钱包子户
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBCustAccountType implements IEnum<GHBCustAccountType> {

    /**
     * 在途子户
     */
    IN_TRSIT("IN_TRSIT", "在途子户"),

    /**
     * 保证金子户
     */
    MARGIN("MARGIN", "保证金子户"),

    /**
     * 钱包子户
     */
    WALLET("WALLET", "钱包子户"),

    ;

    private String code;

    private String name;
}
