package com.xmzy.bank.ghb.enums;

import com.xmzy.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 华兴银行对账文件类型
 * 
 * 文件类型
 * 
 * 2：客户对账文件（只取对账日期的当天新增客户信息）
 * 
 * 3：交易对账文件
 * 
 * 4：余额对账文件
 * 
 * 5：批量分账
 * 
 * 6：增量客户注册信息文件
 * 
 * @author fronttang
 * @date 2021/09/13
 */
@Getter
@AllArgsConstructor
public enum GHBReconciliationFileType implements IEnum<GHBReconciliationFileType> {

    /**
     * 客户对账文件
     */
    CUST("2", "客户对账文件"),

    /**
     * 交易对账文件
     */
    TRANS("3", "交易对账文件"),

    /**
     * 余额对账文件
     */
    BLC("4", "余额对账文件"),

    /**
     * 批量分账
     */
    BATCH_LEDGER("5", "批量分账"),

    /**
     * 增量客户注册信息文件
     */
    REG_CUST("6", "增量客户注册信息文件"),

    ;

    String code;

    String name;
}
