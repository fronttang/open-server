package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 华兴银行客户类型
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBCustType implements IEnum<GHBCustType> {

    /**
     * 个人客户
     */
    QSTP("QSTP", "个人客户"),

    ;

    private String code;

    private String name;
}
