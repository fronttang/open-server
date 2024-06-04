package com.xmzy.bank.ghb;

import org.junit.Test;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fronttang
 * @date 2021/10/15
 */
@Slf4j
public class GHBSMTester {

    @Test
    public void testSM2() {

        SM2 sm2 = SmUtil.sm2();

        String privateKeyHaxStr = HexUtil.encodeHexStr(BCUtil.toParams(sm2.getPrivateKey()).getD().toByteArray());

        log.info("privateKeyHaxStr:00{}", privateKeyHaxStr.toUpperCase());

        String publicKeyHaxStr = HexUtil.encodeHexStr(BCUtil.toParams(sm2.getPublicKey()).getQ().getEncoded(false));

        log.info("publicKeyHaxStr:{}", publicKeyHaxStr.toUpperCase());

        // sm2.getPublicKey();
        //
        // sm2.getPrivateKey();

    }
}
