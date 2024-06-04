package com.xmzy.lhdf.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 车辆类型枚举
 *
 * @author L.D.J
 * @date 2021/09/23 15:42
 **/
@Getter
@AllArgsConstructor
public enum VehicleType implements IEnum<VehicleType> {
    /**
     * 0 客车
     */
    PASSENGER("0", "客车"),

    /**
     * 1 货车
     */
    VAN("1", "货车"),

    /**
     * 2 专项作业车
     */
    SPECIAL_WORK_TRUCK("2", "专项作业车"),

    /**
     * 4 牵引车
     */
    TRACTOR("4", "牵引车");

    private String code;

    private String name;
}
