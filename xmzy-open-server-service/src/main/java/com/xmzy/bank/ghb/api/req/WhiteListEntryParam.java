package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.WhiteListEntryApi;
import com.xmzy.bank.ghb.api.resp.WhiteListEntryVo;

import lombok.Data;

/**
 * @Description:白名单录入请求报文
 */
@Data
@ApiFactory(WhiteListEntryApi.class)
public class WhiteListEntryParam implements IGHBRequestBody, IExecutableApiRequest<WhiteListEntryVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    private String tradeNo;

    /**
     * 商户号
     */
    private String parentMerchantId;

    /**
     * 客户类型 QSTP-个人客户
     */
    private String customerType;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 证件类型
     */
    private String customerIdType;

    /**
     * 证件号码
     */
    private String customerIdNo;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 修改类型 10位控制位：0000000000 控制位1-body1； 控制位2-body2； 控制位3-body3； 控制位4-file1； 控制位5-file2； 不涉及改动默认为0，新增传值1，更新传值2，删除传值3
     */
    private String reqType;

    /**
     * body1参数
     */
    private String body1;

    /**
     * body2参数
     */
    private String body2;

    /**
     * body3参数
     */
    private String body3;

    /***
     * file1参数
     */
    private String file1;

    /**
     * file2参数
     */
    private String file2;

    @Data
    public static class body1 {

        /**
         * 第三方流水号
         */
        private String reqSeqNo;

        /**
         * 手机号
         */
        private String mobile;

        /**
         * 性别
         */
        private String sex;

        /**
         * 证件到期日
         */
        private String certificateDueDate;

        /**
         * 签证机关
         */
        private String issueOrg;

        /**
         * 详细地址
         */
        private String detailAddress;

        /**
         * 邮政编码
         */
        private String postalCode;

        /**
         * 联系地址
         */
        private String contactAddress;

        /**
         * 传真
         */
        private String fax;

        /**
         * 保证金金额
         */
        private String marginAmt;

        /**
         * 学历
         */
        private String academic;

        /**
         * 职业
         */
        private String occupation;

    }
}
