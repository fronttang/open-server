package com.xmzy.bank.ghb.api;

import java.math.BigDecimal;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.api.req.PlatformTransactionParam;
import com.xmzy.bank.ghb.api.resp.PlatformTransactionVo;
import com.xmzy.bank.ghb.enums.GHBBusinessType;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.util.StrUtil;

/**
 * 平台交易接口
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.PLATFORM_TRANSACTION_PATH, name = "平台交易接口")
public class PlatformTransactionApi extends AbstractReqResponseApi<PlatformTransactionParam, PlatformTransactionVo> {

    @Override
    public PlatformTransactionParam beforeExecute(PlatformTransactionParam request) throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        if (StrUtil.isBlank(request.getReqSeqNo())) {
            request.setReqSeqNo(getReqSeqNo(request));
        }

        return super.beforeExecute(request);
    }

    public static PlatformTransactionVo execute(GHBBusinessType businessType, BigDecimal amt, String remark,
        String reqSeqNo) {

        PlatformTransactionParam param = new PlatformTransactionParam();

        param.setTradeNo(reqSeqNo);
        param.setReqSeqNo(reqSeqNo);

        param.setRemark(remark);
        param.setBusinessType(businessType.code());
        param.setAmount(amt.stripTrailingZeros().toPlainString());

        return param.execute();
    }
}
