package com.xmzy.webt.api;

import com.xmzy.api.annotation.Api;
import com.xmzy.webt.api.req.WebtRechargeNotifyParam;
import com.xmzy.webt.api.resp.WebtNotifyVo;

/**
 * 充值成功通知到webt
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Api(host = WebtApiConstants.ACCOUNT_RECHARGE_HOST, path = WebtApiConstants.ACCOUNT_RECHARGE_PATH)
public class WebtRechargeNotifyApi extends AbstractWebtApi<WebtRechargeNotifyParam, WebtNotifyVo> {

    @Override
    protected String getConsmMainSrlNo(WebtRechargeNotifyParam param) {
        return param.getOrderId();
    }
}
