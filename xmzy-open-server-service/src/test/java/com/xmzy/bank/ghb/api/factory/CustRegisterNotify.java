package com.xmzy.bank.ghb.api.factory;

import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.api.CustRegisterNotifyApi;
import com.xmzy.bank.ghb.api.req.CustRegisterNotifyParam;

/**
 * 客户注册成功通知
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@ApiFactory(exec = CustRegisterNotifyApi.class)
public class CustRegisterNotify extends AbstractBankApiFactory<CustRegisterNotifyParam> {

    public CustRegisterNotify(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        super(param, publicKey, privateKey, secretKey);
    }

    public static CustRegisterNotify build(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        return new CustRegisterNotify(param, publicKey, privateKey, secretKey);
    }
}
