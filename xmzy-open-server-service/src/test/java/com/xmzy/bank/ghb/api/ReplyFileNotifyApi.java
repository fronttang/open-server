package com.xmzy.bank.ghb.api;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.api.req.ReplyFileNotifyParam;
import com.xmzy.bank.ghb.api.resp.ReplyFileNotifyVo;
import com.xmzy.base.exception.BusinessException;

/**
 * 回盘文件通知接口
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@GHBApi
public class ReplyFileNotifyApi extends AbstractBankApi<ReplyFileNotifyParam, ReplyFileNotifyVo> {

    @Override
    protected ReplyFileNotifyVo doExecute(ReplyFileNotifyParam request, boolean retry) throws BusinessException {
        return new ReplyFileNotifyVo();
    }

}
