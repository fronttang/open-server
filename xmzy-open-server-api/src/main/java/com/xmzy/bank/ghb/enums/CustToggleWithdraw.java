package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否允许提现 ALLOW-允许 NOALLOW-不允许
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@Getter
@AllArgsConstructor
public enum CustToggleWithdraw implements IEnum<CustToggleWithdraw> {

    /**
     * 允许
     */
    ALLOW("ALLOW", "允许"),

    /**
     * 不允许
     */
    NOALLOW("NOALLOW", "不允许"),

    ;

    private String code;

    private String name;
}
