package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 银行客户状态
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Getter
@AllArgsConstructor
public enum GHBCustStatus implements IEnum<GHBCustStatus> {

    /**
     * 正常
     */
    NORMAL("0", "正常"),

    ;

    private String code;

    private String name;
}
