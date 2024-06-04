package com.xmzy.lhdf.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检查车辆是否可以申请结果
 *
 * @author L.D.J
 * @date 2021/09/24 14:20
 **/
@Getter
@AllArgsConstructor
public enum CheckVehicleResultCode implements IEnum<CheckVehicleResultCode> {
    /**
     * 0000:允许办理
     */
    ALLOW("0000", "允许办理"),

    /**
     * 0001:不允许办理,车牌已占用
     */
    NOT_ALLOW("0001", "不允许办理,车牌已占用");

    private String code;

    private String name;
}
