package com.xmzy.bank.ghb.cipher;

import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.base.exception.BusinessException;

/**
 * 银行参数加解密处理类，为了对参数进行加签验签加密、解密等操作
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
public interface IGHBCipher<T> {

    /**
     * 解签、解密等
     * 
     * @param cipher
     *            密文对象
     * @return 解密后的对象
     * @throws BusinessException
     */
    T decode(T cipher) throws GHBBusinessException;

    /**
     * 加签、加密等
     * 
     * @param source
     *            源文对象
     * @return 加密后的对象
     * @throws BusinessException
     */
    T encode(T source) throws GHBBusinessException;

}
