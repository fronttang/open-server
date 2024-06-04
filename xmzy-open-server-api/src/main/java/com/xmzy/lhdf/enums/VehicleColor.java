package com.xmzy.lhdf.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 车牌颜色枚举
 *
 * @author L.D.J
 * @date 2021/09/23 15:18
 **/
@Getter
@AllArgsConstructor
public enum VehicleColor implements IEnum<VehicleColor> {
    /**
     * 0 蓝色
     */
    BLUE("0", "蓝色"),

    /**
     * 1 黄色
     */
    YELLOW("1", "黄色"),

    /**
     * 2 黑色
     */
    BLACK("2", "黑色"),

    /**
     * 3 白色
     */
    WHITE("3", "白色"),

    /**
     * 4 渐变绿色
     */
    GREEN("4", "渐变绿色"),

    /**
     * 5 黄绿渐变色
     */
    YELLOW_AND_GREEN("5", "黄绿渐变色"),

    /**
     * 9 蓝白渐变色
     */
    BLUE_AND_WHITE("9", "蓝白渐变色");

    private String code;

    private String name;
}
