package com.xmzy.bank.ghb.api;

import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.GHBTransCode;
import com.xmzy.bank.ghb.api.req.PlatformAccountBalanceParam;
import com.xmzy.bank.ghb.api.resp.PlatformAccountBalanceVo;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.util.StrUtil;

/**
 * 平台台账余额查询
 * 
 * @author fronttang
 * @date 2021/08/30
 */
@GHBApi(path = GHBApiConstants.PLATFORM_ACCOUNT_BALANCE_PATH, name = "平台台账余额查询")
public class PlatformAccountBalanceApi extends AbstractGHBApi<PlatformAccountBalanceParam, PlatformAccountBalanceVo> {

    @Override
    public PlatformAccountBalanceParam beforeExecute(PlatformAccountBalanceParam request) throws BusinessException {

        if (StrUtil.isBlank(request.getParentMerchantId())) {
            request.setParentMerchantId(properties.getParentMerchantId());
        }

        if (StrUtil.isBlank(request.getTransCode())) {
            request.setTransCode(GHBTransCode.FCMQ0011);
        }

        return super.beforeExecute(request);
    }

    @Override
    public PlatformAccountBalanceVo afterExecute(PlatformAccountBalanceParam request, PlatformAccountBalanceVo response)
        throws BusinessException {

        if (!GHBResultCode.SUCCESS.equals(response.getErrorCode())) {
            throw new BusinessException(response);
        }

        return super.afterExecute(request, response);
    }

    public static PlatformAccountBalanceVo exec() {

        PlatformAccountBalanceParam param = new PlatformAccountBalanceParam();
        return param.execute();
    }
}
