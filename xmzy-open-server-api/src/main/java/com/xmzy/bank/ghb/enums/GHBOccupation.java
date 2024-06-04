package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 职业
 * 
 * 1 国家机关党群组织企业事业单位负债人
 * 
 * 2 专业技术人员
 * 
 * 3 办事人员
 * 
 * 4 商业工作人员
 * 
 * 5 服务性工作人员
 * 
 * 6 农林牧渔水利业生产人员
 * 
 * 7 生产工人、运输工人
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBOccupation implements IEnum<GHBOccupation> {

    /**
     * 国家机关党群组织企业事业单位负债人
     */
    NO1("1", "国家机关党群组织企业事业单位负债人"),

    /**
     * 专业技术人员
     */
    NO2("2", "专业技术人员"),

    /**
     * 办事人员
     */
    NO3("3", "办事人员"),

    /**
     * 商业工作人员
     */
    NO4("4", "商业工作人员"),

    /**
     * 服务性工作人员
     */
    NO5("5", "服务性工作人员"),

    /**
     * 农林牧渔水利业生产人员
     */
    NO6("6", "农林牧渔水利业生产人员"),

    /**
     * 生产工人、运输工人
     */
    NO7("7", "生产工人、运输工人"),

    ;

    private String code;

    private String name;
}
