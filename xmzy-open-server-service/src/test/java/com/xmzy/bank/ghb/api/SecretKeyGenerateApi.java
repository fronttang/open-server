package com.xmzy.bank.ghb.api;

import java.time.LocalDateTime;

import javax.crypto.SecretKey;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.api.req.GetSecretKeyParam;
import com.xmzy.bank.ghb.api.resp.GetSecretKeyVo;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 生成加密密钥接口（银行调用）
 * 
 * @author fronttang
 * @date 2021/08/27
 */
@GHBApi
public class SecretKeyGenerateApi extends AbstractBankApi<GetSecretKeyParam, GetSecretKeyVo> {

    @Override
    protected GetSecretKeyVo doExecute(GetSecretKeyParam request, boolean retry) throws BusinessException {

        SecretKey generateKey = SecureUtil.generateKey("SM4");
        String secretKey = HexUtil.encodeHexStr(generateKey.getEncoded()).toUpperCase();

        String applyTime = DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_MS_PATTERN);

        // RedisUtil.set(BankCacheConstants.getSecertKey(BankConstants.BANK_ID), applyTime);

        return new GetSecretKeyVo(secretKey, applyTime);
    }

    public static void main(String[] args) {
        SecretKey generateKey = SecureUtil.generateKey("SM4");
        String secretKey = HexUtil.encodeHexStr(generateKey.getEncoded()).toUpperCase();
        System.out.println(secretKey);
    }

}
