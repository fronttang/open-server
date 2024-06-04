package com.xmzy.webt.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.webt.IWebtRequestBody;
import com.xmzy.webt.api.WebtCustRegisterNotifyApi;
import com.xmzy.webt.api.resp.WebtNotifyVo;

import lombok.Data;

/**
 * 客户注册成功请求报文
 */
@Data
@ApiFactory(WebtCustRegisterNotifyApi.class)
public class WebtCustRegisterNotifyParam implements IWebtRequestBody, IExecutableApiRequest<WebtNotifyVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 银行客户编号
     */
    private String merchantId;

    /**
     * 第三方流水号
     */
    private String reqSeqNo;

    /**
     * 客户状态
     */
    private String custStatus;

    /**
     * 客户ID
     */
    private String userId;

}
