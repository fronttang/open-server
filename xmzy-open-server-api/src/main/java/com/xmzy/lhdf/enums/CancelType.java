package com.xmzy.lhdf.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注销方式
 *
 * @author L.D.J
 * @date 2021/09/24 14:20
 **/
@Getter
@AllArgsConstructor
public enum CancelType implements IEnum<CancelType> {
    /**
     * 1 无卡注销
     */
    OBLIGATIONS("1", "无卡注销"),

    /**
     * 2：有卡注销
     */
    TO_BE_SEND("2", "有卡注销");

    private String code;

    private String name;
}
