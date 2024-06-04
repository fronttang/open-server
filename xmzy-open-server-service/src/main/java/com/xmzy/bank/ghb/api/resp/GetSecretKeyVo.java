package com.xmzy.bank.ghb.api.resp;

import com.xmzy.bank.ghb.IGHBResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加密密钥返回
 * 
 * @author fronttang
 * @date 2021/08/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSecretKeyVo implements IGHBResponseBody {

    private static final long serialVersionUID = 1L;

    /**
     * 加密密钥
     */
    private String appSecret;

    /**
     * 申请时间
     */
    private String applyTime;

}
