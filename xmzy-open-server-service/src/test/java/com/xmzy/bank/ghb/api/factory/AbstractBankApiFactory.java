package com.xmzy.bank.ghb.api.factory;

import java.time.LocalDateTime;

import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSON;
import com.xmzy.api.IApi;
import com.xmzy.api.IApiRequest;
import com.xmzy.api.factory.AbstractApiFactory;
import com.xmzy.api.util.ApiUtils;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.GHBRequestHeader;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.GHBResponseHeader;
import com.xmzy.bank.ghb.IGHBRequestBody;
import com.xmzy.bank.ghb.IGHBResponseBody;
import com.xmzy.bank.ghb.cipher.GHBRequestCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;
import com.xmzy.base.exception.BusinessException;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * 银行调用factory，用于处理华兴银行发来的请求
 * 
 * @author FrontTang
 * @date 2021/08/28
 */
public abstract class AbstractBankApiFactory<T extends IGHBRequestBody> extends AbstractApiFactory<GHBResponse> {

    /**
     * 银行公钥
     */
    protected String publicKey;

    /**
     * 小马私钥
     */
    protected String privateKey;

    /**
     * 加密密钥
     */
    protected String secretKey;

    /**
     * 银行请求参数
     */
    protected GHBRequest param;

    public AbstractBankApiFactory(GHBRequest param, String publicKey, String privateKey, String secretKey) {
        super(param);
        this.param = param;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.secretKey = secretKey;
    }

    @Override
    public GHBResponse execute() throws BusinessException {

        GHBRequest request = this.param;
        GHBRequestHeader requestHeader = request.getHeader();

        // 对请求参数进行解密验签
        IGHBCipher<GHBRequest> requestWrapper = getRequestCipher(request);
        request = requestWrapper.decode(request);

        String requestBodyStr = String.valueOf(request.getBody());

        T parseObject = JSON.parseObject(requestBodyStr, getGenericType());

        IGHBResponseBody responseBody = getApiImpl().execute(parseObject);

        GHBResponse response = new GHBResponse();
        GHBResponseHeader responseHeader = new GHBResponseHeader();
        responseHeader.setAppId(requestHeader.getAppId());

        // TODO 先每次调用都生成
        LocalDateTime now = LocalDateTime.now();
        String responseTime = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
        String responseId = responseTime + RandomUtil.randomNumbers(6);

        responseHeader.setRequestId(requestHeader.getRequestId());
        responseHeader.setResponseId(responseId);
        responseHeader.setResponseTime(responseTime);
        response.setHeader(responseHeader);
        response.setBody(responseBody);

        // 对参数进行加密、加签
        IGHBCipher<GHBResponse> responseWrapper = getResponseCipher(response);
        response = responseWrapper.encode(response);

        return response;
    }

    /**
     * 取得返回包装类
     * 
     * @param response
     * @return
     */
    protected IGHBCipher<GHBResponse> getResponseCipher(GHBResponse response) {
        return new GHBResponseCipher(publicKey, privateKey, secretKey);
    }

    /**
     * 得到请求包装类
     * 
     * @param request
     * @return
     */
    protected IGHBCipher<GHBRequest> getRequestCipher(GHBRequest request) {
        return new GHBRequestCipher(publicKey, privateKey, secretKey);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public IApi<IApiRequest, IGHBResponseBody> getApiImpl() throws BusinessException {
        IApi api = ApiUtils.getApi(this.getApiClass());
        return api;
    }

    /**
     * 获取泛型类型
     * 
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getGenericType() {
        Class<?> userClass = ClassUtils.getUserClass(getClass());
        ResolvableType resolvableType = ResolvableType.forClass(userClass);

        return (Class<T>)resolvableType.as(AbstractBankApiFactory.class).getGeneric(0).resolve();
    }

}
