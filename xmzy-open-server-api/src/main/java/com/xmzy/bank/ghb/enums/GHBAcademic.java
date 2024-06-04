package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学历字典 00 博士 10 研究生 20 大学本科 30 大专 40 中专 50 技校 60 高中 70 初中 80 小学 90 文盲或半文盲 99 未知
 * 
 * @author fronttang
 * @date 2021/09/02
 */
@Getter
@AllArgsConstructor
public enum GHBAcademic implements IEnum<GHBAcademic> {

    /**
     * 博士
     */
    DOCTOR("00", "博士"),

    /**
     * 研究生
     */
    POSTGRADUATE("10", "研究生"),

    /**
     * 大学本科
     */
    UNDERGRADUATE("20", "大学本科"),

    /**
     * 大专
     */
    COLLEGE("30", "大专"),

    /**
     * 中专
     */
    SPECIALIZED("40", "中专"),

    /**
     * 技校
     */
    TECHNICAL("50", "技校"),

    /**
     * 高中
     */
    SENIOR_MIDDLE_SCHOOL("60", "高中"),

    /**
     * 初中
     */
    JUNIOR_MIDDLE_SCHOOL("70", "初中"),

    /**
     * 小学
     */
    PRIMARY_SCHOOL("80", "小学"),

    /**
     * 文盲或半文盲
     */
    ILLITERACY("90", "文盲或半文盲"),

    /**
     * 未知
     */
    UNKNOWN("99", "未知"),;

    private String code;

    private String name;
}
