package com.xmzy.bank.ghb.api;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.api.req.RechargeNotifyParam;
import com.xmzy.bank.ghb.api.resp.RechargeNotifyVo;
import com.xmzy.base.exception.BusinessException;

/**
 * 充值成功通知
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@GHBApi
public class RechargeNotifyApi extends AbstractBankApi<RechargeNotifyParam, RechargeNotifyVo> {

    @Override
    protected RechargeNotifyVo doExecute(RechargeNotifyParam request, boolean retry) throws BusinessException {
        return new RechargeNotifyVo();
    }

}
