package com.xmzy.lhdf.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author L.D.J
 * @date 2021/09/23 15:26
 **/
@Getter
@AllArgsConstructor
public enum OrderSts implements IEnum<OrderSts> {
    /**
     * 0 待付款
     */
    OBLIGATIONS("0", "待付款"),

    /**
     * 1 待发货（已付款）
     */
    TO_BE_SEND("1", "待发货（已付款）"),

    /**
     * 2 审核失败
     */
    AUDIT_FAILED("2", "审核失败"),

    /**
     * 3 待收货（审核成功，已发货）
     */
    TO_BE_RECEIVE("3", "待收货（审核成功，已发货）"),

    /**
     * 4 待激活
     */
    TO_BE_ACTIVATE("4", "待激活"),

    /**
     * 5 已完成
     */
    IS_DONE("5", "已完成"),

    /**
     * 6 已取消
     */
    IS_CANCELLED("6", "已取消"),

    /**
     * 7 退款审核中
     */
    REFUND_UNDER_REVIEW("7", "退款审核中"),

    /**
     * 8 已退款
     */
    IS_REFUND("8", "已退款"),

    /**
     * 9 退货审核中
     */
    RETURN_UNDER_REVIEW("9", "退货审核中"),

    /**
     * 10 退货审核失败
     */
    RETURN_AUDIT_FAILED("10", "退货审核失败"),

    /**
     * 11 已退货
     */
    IS_RETURN("11", "已退货"),

    /**
     * 12 已付款待审核
     */
    PAYMENT_PENDING_REVIEW("12", "已付款待审核"),

    /**
     * 13 退货审核成功
     */
    RETURN_AUDIT_SUCCESS("13", "退货审核成功"),

    /**
     * 14 退款失败
     */
    REFUND_FAILED("14", "退款失败"),

    /**
     * 16 待签约
     */
    TO_BE_SIGN_UP("16", "待签约");

    private String code;

    private String name;
}
