package com.xmzy.bank.ghb.api.factory;

import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.api.ReplyFileNotifyApi;
import com.xmzy.bank.ghb.api.req.ReplyFileNotifyParam;

/**
 * 回盘文件通知
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
@ApiFactory(exec = ReplyFileNotifyApi.class)
public class ReplyFileNotify extends AbstractBankApiFactory<ReplyFileNotifyParam> {

    private ReplyFileNotify(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        super(param, publicKey, privateKey, secretKey);
    }

    public static ReplyFileNotify build(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        return new ReplyFileNotify(param, publicKey, privateKey, secretKey);
    }
}
