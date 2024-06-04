package com.xmzy.bank.ghb;

/**
 * GBH 华兴银行报文接口
 * 
 * @author fronttang
 * @date 2021/10/12
 */
public interface IGHBMessage {

    /**
     * 报文头
     * 
     * @return
     */
    GHBHeader getHeader();

    /**
     * 报文体
     * 
     * @return
     */
    Object getBody();

    /**
     * set 报文体
     */
    void setBody(Object body);
}
