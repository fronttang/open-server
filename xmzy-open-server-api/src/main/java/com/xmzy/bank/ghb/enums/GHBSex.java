package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 华兴银行性别 女0 男1
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBSex implements IEnum<GHBSex> {

    /**
     * 男
     */
    MALE("1", "男"),

    /**
     * 女
     */
    FEMALE("0", "女"),;

    private String code;

    private String name;
}
