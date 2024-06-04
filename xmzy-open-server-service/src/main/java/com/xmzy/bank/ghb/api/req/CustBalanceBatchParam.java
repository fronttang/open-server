package com.xmzy.bank.ghb.api.req;

import java.util.List;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.CustBalanceBatchApi;
import com.xmzy.bank.ghb.api.resp.CustBalanceBatchVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:客户台账余额批量查询请求报文
 */
@Data
@ApiFactory(CustBalanceBatchApi.class)
public class CustBalanceBatchParam implements IGHBRequestBody, IExecutableApiRequest<CustBalanceBatchVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易码
     */
    private String transCode;

    /**
     * 合作商编号
     */
    private String parentMerchantId;

    /**
     * 客户编号集合
     */
    private List<MerchantIdList> merchantIdList;

    /**
     * 每页条数
     */
    private String pageCount;

    /**
     * 查询页码
     */
    private String currentPage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MerchantIdList {
        /**
         * 银行客户编号
         */
        private String merchantId;

    }

}
