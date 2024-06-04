package com.xmzy.webt.service;

import com.xmzy.webt.dto.BillNotifyDto;

/**
 * 客户金币service
 * 
 * @author fronttang
 * @date 2021/08/30
 */
public interface CustCoinService {

    /**
     * 服务费扣费成功增加客户金币
     * 
     * @param param
     * @return
     */
    boolean addCustCoin(BillNotifyDto param);
}
