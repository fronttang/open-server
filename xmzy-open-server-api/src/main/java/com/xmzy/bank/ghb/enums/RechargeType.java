package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 充值类型 1-可用余额充值 2-保证金充值
 * 
 * @author fronttang
 * @date 2021/10/12
 */
@Getter
@AllArgsConstructor
public enum RechargeType implements IEnum<RechargeType> {

    /**
     * 可用余额充值
     */
    WALLET("1", "可用余额充值"),

    /**
     * 保证金充值
     */
    MARGIN("2", "保证金充值")

    ;

    String code;

    String name;
}
