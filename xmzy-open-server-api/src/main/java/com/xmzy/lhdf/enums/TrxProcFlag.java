package com.xmzy.lhdf.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ETC变更模式
 *
 * @author L.D.J
 * @date 2021/09/24 14:20
 **/
@Getter
@AllArgsConstructor
public enum TrxProcFlag implements IEnum<TrxProcFlag> {
    /**
     * 2-发货（邮寄）
     */
    MAIL("2", "发货（邮寄）"),
    /**
     * 3-激活
     */
    ACTIVITY("3", "激活"),
    /**
     * 4-产品更换
     */
    CHANGE("4", "产品更换");

    private String code;

    private String name;
}
