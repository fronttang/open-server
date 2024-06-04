package com.xmzy.bank.ghb;

/**
 * @author fronttang
 * @date 2021/09/02
 */
public interface GHBTransCode {

    /**
     * 查询客户余额
     */
    String FCMQ0010 = "FCMQ0010";

    /**
     * 查询平台余额
     */
    String FCMQ0011 = "FCMQ0011";

    /**
     * 客户开放提现
     */
    String FCMM0011 = "FCMM0011";

    /**
     * 补缴保证金通知
     */
    String FCMM0014 = "FCMM0014";

    /**
     * 回盘文件
     */
    String FCMT0008 = "FCMT0008";

    /**
     * 对账文件
     */
    String FCMB0001 = "FCMB0001";

}
