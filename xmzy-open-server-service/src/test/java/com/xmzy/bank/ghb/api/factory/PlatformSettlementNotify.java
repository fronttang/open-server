package com.xmzy.bank.ghb.api.factory;

import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.api.PlatformSettlementNotifyApi;
import com.xmzy.bank.ghb.api.req.PlatformSettlementNotifyParam;

/**
 * 平台结算结果通知
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@ApiFactory(exec = PlatformSettlementNotifyApi.class)
public class PlatformSettlementNotify extends AbstractBankApiFactory<PlatformSettlementNotifyParam> {

    private PlatformSettlementNotify(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        super(param, publicKey, privateKey, secretKey);
    }

    public static PlatformSettlementNotify build(GHBRequest param, String publicKey, String privateKey,
        String secretKey) {
        return new PlatformSettlementNotify(param, publicKey, privateKey, secretKey);
    }
}
