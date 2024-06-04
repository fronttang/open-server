package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 证件类型
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBCustIdType implements IEnum<GHBCustIdType> {

    /**
     * 居民身份证
     */
    ID_CARD("1010", "居民身份证"),

    ;

    private String code;

    private String name;
}
