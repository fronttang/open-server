package com.xmzy.bank.ghb.api;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.GHBTransCode;
import com.xmzy.bank.ghb.api.req.CustOpenWithdrawalParam;
import com.xmzy.bank.ghb.api.resp.CustOpenWithdrawalVo;
import com.xmzy.bank.ghb.enums.CustToggleWithdraw;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.util.StrUtil;

/**
 * 客户开放提现功能
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.CUST_OPEN_WITHDRAWAL_PATH, name = "客户开放提现")
public class CustOpenWithdrawalApi extends AbstractGHBApi<CustOpenWithdrawalParam, CustOpenWithdrawalVo> {

    @Override
    public CustOpenWithdrawalParam beforeExecute(CustOpenWithdrawalParam request) throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        if (StrUtil.isBlank(request.getTransCode())) {
            request.setTransCode(GHBTransCode.FCMM0011);
        }

        if (StrUtil.isBlank(request.getReqSeqNo())) {
            request.setReqSeqNo(getReqSeqNo(request));
        }

        return super.beforeExecute(request);
    }

    @Override
    public CustOpenWithdrawalVo afterExecute(CustOpenWithdrawalParam request, CustOpenWithdrawalVo response)
        throws BusinessException {

        if (!response.isSuccess()) {
            throw new BusinessException(response);
        }

        return super.afterExecute(request, response);
    }

    public static CustOpenWithdrawalVo allow(String custBankId) {

        CustOpenWithdrawalParam param = new CustOpenWithdrawalParam();
        param.setMerchantId(custBankId);
        param.setWithdraw(CustToggleWithdraw.ALLOW.code());

        return param.execute();
    }

    public static CustOpenWithdrawalVo execute(String custBankId, CustToggleWithdraw withdraw) {

        CustOpenWithdrawalParam param = new CustOpenWithdrawalParam();
        param.setMerchantId(custBankId);
        param.setWithdraw(withdraw.code());

        return param.execute();
    }
}
