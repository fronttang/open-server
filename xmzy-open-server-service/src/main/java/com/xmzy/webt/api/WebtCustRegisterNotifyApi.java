package com.xmzy.webt.api;

import com.xmzy.api.annotation.Api;
import com.xmzy.webt.WebtResponse;
import com.xmzy.webt.WebtResponseSysHeader;
import com.xmzy.webt.api.req.WebtCustRegisterNotifyParam;
import com.xmzy.webt.api.resp.WebtNotifyVo;

/**
 * 客户注册成功通知到webt
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Api(host = WebtApiConstants.OPEN_ACCOUNT_CALLBACK_HOST, path = WebtApiConstants.OPEN_ACCOUNT_CALLBACK_PATH)
public class WebtCustRegisterNotifyApi extends AbstractWebtApi<WebtCustRegisterNotifyParam, WebtNotifyVo> {

    // @Override
    // protected String getConsmMainSrlNo(WebtCustRegisterNotifyParam param) {
    // return param.getReqSeqNo();
    // }

    @Override
    protected boolean isSuccess(WebtResponse result) {

        WebtResponseSysHeader sysHead = result.getSysHead();

        // 该客户已开立虚拟账户
        if ("300002".equals(sysHead.getRetCd())) {
            return true;
        }

        return super.isSuccess(result);
    }
}
