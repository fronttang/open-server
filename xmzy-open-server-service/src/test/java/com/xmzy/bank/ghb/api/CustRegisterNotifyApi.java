package com.xmzy.bank.ghb.api;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.api.req.CustRegisterNotifyParam;
import com.xmzy.bank.ghb.api.resp.CustRegisterNotifyVo;
import com.xmzy.base.exception.BusinessException;

/**
 * 客户注册成功通知接口
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@GHBApi
public class CustRegisterNotifyApi extends AbstractBankApi<CustRegisterNotifyParam, CustRegisterNotifyVo> {

    @Override
    protected CustRegisterNotifyVo doExecute(CustRegisterNotifyParam request, boolean retry) throws BusinessException {
        CustRegisterNotifyVo result = new CustRegisterNotifyVo();
        result.setReturnCode("0000");
        result.setReturnMessage("成功");
        return result;
    }

}
