package com.xmzy.bank.ghb.api.req;

import com.xmzy.api.IExecutableApiRequest;
import com.xmzy.api.annotation.ApiFactory;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.api.GetSecretKeyApi;
import com.xmzy.bank.ghb.api.resp.GetSecretKeyVo;

import lombok.Data;

/**
 * 加密密钥入参
 * 
 * @author fronttang
 * @date 2021/08/27
 */
@Data
@ApiFactory(GetSecretKeyApi.class)
public class GetSecretKeyParam implements IGHBRequestBody, IExecutableApiRequest<GetSecretKeyVo> {

    private static final long serialVersionUID = 1L;

}
