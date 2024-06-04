package com.xmzy.bank.ghb.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.GHBTransCode;
import com.xmzy.bank.ghb.api.req.CustBalanceBatchParam;
import com.xmzy.bank.ghb.api.req.CustBalanceBatchParam.MerchantIdList;
import com.xmzy.bank.ghb.api.resp.CustBalanceBatchVo;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

/**
 * 客户台账余额批量查询
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.CUST_BALANCE_BATCH_PATH, name = "客户台账余额批量查询")
public class CustBalanceBatchApi extends AbstractGHBApi<CustBalanceBatchParam, CustBalanceBatchVo> {

    @Override
    public CustBalanceBatchParam beforeExecute(CustBalanceBatchParam request) throws BusinessException {

        if (StrUtil.isBlank(request.getTransCode())) {
            request.setTransCode(GHBTransCode.FCMQ0010);
        }

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        return super.beforeExecute(request);
    }

    /**
     * 
     * @param custBankIds
     * @return
     */
    public static CustBalanceBatchVo execute(@NotNull List<String> custBankIds, Integer page, Integer pageSize) {

        if (CollUtil.isEmpty(custBankIds)) {
            throw new BusinessException("银行客户编号不能为空");
        }

        CustBalanceBatchParam param = new CustBalanceBatchParam();

        List<MerchantIdList> collect = custBankIds.stream().map((custNo) -> {
            MerchantIdList merchantId = new MerchantIdList(custNo);
            return merchantId;
        }).collect(Collectors.toList());

        param.setMerchantIdList(collect);
        param.setCurrentPage(Convert.toStr(page));
        param.setPageCount(Convert.toStr(pageSize));

        return param.execute();
    }
}
