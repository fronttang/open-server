package com.xmzy.bank.ghb.api;

import java.math.BigDecimal;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.GHBTransCode;
import com.xmzy.bank.ghb.api.req.CustSupplementaryDepositNotifyParam;
import com.xmzy.bank.ghb.api.resp.CustSupplementaryDepositNotifyVo;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.util.StrUtil;

/**
 * 客户补缴保证金通知
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.CUST_SUPPLEMENTARY_DEPOSIT_NOTIFY_PATH, name = "客户补缴保证金通知")
public class CustSupplementaryDepositNotifyApi
    extends AbstractGHBApi<CustSupplementaryDepositNotifyParam, CustSupplementaryDepositNotifyVo> {

    @Override
    public CustSupplementaryDepositNotifyParam beforeExecute(CustSupplementaryDepositNotifyParam request)
        throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        if (StrUtil.isBlank(request.getTransCode())) {
            request.setTransCode(GHBTransCode.FCMM0014);
        }

        if (StrUtil.isBlank(request.getReqSeqNo())) {
            request.setReqSeqNo(getReqSeqNo(request));
        }

        return super.beforeExecute(request);
    }

    @Override
    public CustSupplementaryDepositNotifyVo afterExecute(CustSupplementaryDepositNotifyParam request,
        CustSupplementaryDepositNotifyVo response) throws BusinessException {

        if (!response.isSuccess()) {
            throw new BusinessException(response);
        }

        return super.afterExecute(request, response);
    }

    public static CustSupplementaryDepositNotifyVo execute(String custBankId, BigDecimal amt, String reqSeqNo) {

        CustSupplementaryDepositNotifyParam param = new CustSupplementaryDepositNotifyParam();
        param.setMerchantId(custBankId);

        // 对小数点后转String
        param.setMarginAmt(amt.stripTrailingZeros().toPlainString());
        param.setReqSeqNo(reqSeqNo);

        return param.execute();
    }
}
