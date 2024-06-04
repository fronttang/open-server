package com.xmzy.bank.ghb.api;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.api.req.PlatformSettlementNotifyParam;
import com.xmzy.bank.ghb.api.resp.PlatformSettlementNotifyVo;
import com.xmzy.base.exception.BusinessException;

/**
 * 平台结算结果通知接口
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@GHBApi
public class PlatformSettlementNotifyApi
    extends AbstractBankApi<PlatformSettlementNotifyParam, PlatformSettlementNotifyVo> {

    @Override
    protected PlatformSettlementNotifyVo doExecute(PlatformSettlementNotifyParam request, boolean retry)
        throws BusinessException {
        return new PlatformSettlementNotifyVo();
    }

}
