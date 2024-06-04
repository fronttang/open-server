package com.xmzy.bank.ghb.api.factory;

import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.api.RechargeNotifyApi;
import com.xmzy.bank.ghb.api.req.RechargeNotifyParam;

/**
 * 充值成功通知
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@ApiFactory(exec = RechargeNotifyApi.class)
public class RechargeNotify extends AbstractBankApiFactory<RechargeNotifyParam> {

    private RechargeNotify(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        super(param, publicKey, privateKey, secretKey);
    }

    public static RechargeNotify build(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        return new RechargeNotify(param, publicKey, privateKey, secretKey);
    }
}
