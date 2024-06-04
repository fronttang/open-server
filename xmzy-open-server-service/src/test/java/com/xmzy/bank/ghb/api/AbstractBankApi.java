package com.xmzy.bank.ghb.api;

import com.xmzy.api.AbstractApi;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.IGHBResponseBody;

/**
 * 抽象提供给银行的接口
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
public abstract class AbstractBankApi<P extends IGHBRequestBody, R extends IGHBResponseBody> extends AbstractApi<P, R> {

}
