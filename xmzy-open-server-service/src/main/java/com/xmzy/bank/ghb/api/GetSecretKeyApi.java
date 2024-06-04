package com.xmzy.bank.ghb.api;

import java.util.Objects;

import com.xmzy.bank.ghb.GHBApi;
import com.xmzy.bank.ghb.GHBApiConstants;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.api.req.GetSecretKeyParam;
import com.xmzy.bank.ghb.api.resp.GetSecretKeyVo;
import com.xmzy.bank.ghb.cipher.GHBRequestAesKeyCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseAesKeyCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;
import com.xmzy.bank.ghb.util.SecretKeyHolder;
import com.xmzy.base.exception.BusinessException;

/**
 * 向银行请求加密密钥接口
 * 
 * @author fronttang
 * @date 2021/08/27
 */
@GHBApi(path = GHBApiConstants.AES_KEY_GENERATE_PATH, name = "请求加密密钥")
public class GetSecretKeyApi extends AbstractGHBApi<GetSecretKeyParam, GetSecretKeyVo> {

    @Override
    public GetSecretKeyVo afterExecute(GetSecretKeyParam param, GetSecretKeyVo result) throws BusinessException {
        // 将返回的结果存入REDIS缓存起来

        if (Objects.nonNull(result)) {

            SecretKeyHolder.setSecretKey(result);
        }

        return super.afterExecute(param, result);
    }

    @Override
    protected IGHBCipher<GHBRequest> getRequestCipher() {
        return new GHBRequestAesKeyCipher(publicKey, privateKey);
    }

    @Override
    protected IGHBCipher<GHBResponse> getResponseCipher() {
        return new GHBResponseAesKeyCipher(publicKey, privateKey);
    }

    @Override
    protected void init() {
        super.init();
        // this.privateKey = GHBConstants.BANK_PRIVATE_KEY;
        // this.publicKey = GHBConstants.XM_PUBLIC_KEY;
        // this.host = "http://127.0.0.1:8091";
        // this.path = "/ghb/secret/generate";
    }

    public static GetSecretKeyVo exec() {

        return new GetSecretKeyParam().execute();

        // GetSecretKeyApi api = ApiUtils.getApi(GetSecretKeyApi.class);
        // return api.execute(new GetSecretKeyParam());
    }
}
