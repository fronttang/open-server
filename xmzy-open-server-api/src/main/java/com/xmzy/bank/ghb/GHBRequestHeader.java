package com.xmzy.bank.ghb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 银行接口请求报文header
 * 
 * @author fronttang
 * @date 2021/08/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GHBRequestHeader extends GHBHeader {

    /**
     * 请求时间，格式：yyyyMMddHHmmssSSS requestTime与requestId前17位需一致
     */
    private String requestTime;
}
