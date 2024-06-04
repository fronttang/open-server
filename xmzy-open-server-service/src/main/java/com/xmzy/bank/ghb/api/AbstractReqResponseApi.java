package com.xmzy.bank.ghb.api;

import com.xmzy.bank.ghb.GHBResponseBody;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.base.exception.BusinessException;

/**
 * 抽象业务状态码返回接口
 * 
 * @author fronttang
 * @date 2021/09/16
 */
public abstract class AbstractReqResponseApi<P extends IGHBRequestBody, R extends GHBResponseBody>
    extends AbstractGHBApi<P, R> {

    @Override
    public R afterExecute(P request, R response) throws BusinessException {
        if (!response.isSuccess()) {
            throw new BusinessException(response);
        }
        return super.afterExecute(request, response);
    }
}
