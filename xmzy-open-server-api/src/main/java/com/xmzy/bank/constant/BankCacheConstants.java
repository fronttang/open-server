package com.xmzy.bank.constant;

import cn.hutool.core.util.StrUtil;

/**
 * 银行接口缓存相关常理
 * 
 * @author fronttang
 * @date 2021/08/27
 */
public interface BankCacheConstants {

    /**
     * 加密密钥
     */
    String BANK_SECRET_KEY = "BANK:SECRET";

    /**
     * 获取机构的加密密钥
     * 
     * @param org
     * @return
     */
    static String getSecretKey(String org) {
        return StrUtil.format("{}:{}", BANK_SECRET_KEY, org);
    }

    /**
     * 获取机构的旧加密密钥
     */
    static String getSecretKeyOld(String org) {
        return StrUtil.format("{}:{}:{}", BANK_SECRET_KEY, "OLD", org);
    }
}
