package com.xmzy.webt.api;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmzy.api.AbstractHttpClientApi;
import com.xmzy.api.enums.ApiResultEnum;
import com.xmzy.bank.constant.WebtProperties;
import com.xmzy.base.exception.BusinessException;
import com.xmzy.server.base.util.HttpRequestUtil;
import com.xmzy.server.base.util.RestTemplateUtils;
import com.xmzy.webt.IWebtRequestBody;
import com.xmzy.webt.IWebtResponseBody;
import com.xmzy.webt.WebtRequest;
import com.xmzy.webt.WebtRequestSysHeader;
import com.xmzy.webt.WebtResponse;
import com.xmzy.webt.WebtResponseSysHeader;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象维恩贝特接口
 * 
 * @author fronttang
 * @date 2021/09/01
 */
@Slf4j
public abstract class AbstractWebtApi<P extends IWebtRequestBody, R extends IWebtResponseBody>
    extends AbstractHttpClientApi<P, R> {

    @Autowired
    protected WebtProperties properties;

    @Override
    protected R doExecute(P request, boolean retry) throws BusinessException {

        try {

            // 执行调用
            WebtResponse result = RestTemplateUtils.exchange(getRequestUri(request), this.method, getRequestHeader(),
                getRequestBody(request), WebtResponse.class);

            // 后置处理
            R res = handleResult(request, result);

            return res;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ApiResultEnum.API_CALL_FAIL);
        }
    }

    @Override
    protected URI getRequestUri(P param) {
        return URI.create(properties.getHost());
    }

    /**
     * 处理结果
     * 
     * @param request
     * @param result
     * @return
     */
    private R handleResult(P request, WebtResponse result) {

        if (isSuccess(result)) {
            JSONObject body = result.getBody();
            if (Objects.nonNull(body)) {
                return body.toJavaObject(getResponseType());
            }
        }
        return null;
    }

    /**
     * 成功判断
     */
    protected boolean isSuccess(WebtResponse result) {

        WebtResponseSysHeader sysHead = result.getSysHead();

        if (!sysHead.isSuccess()) {
            throw new BusinessException(sysHead);
        }

        return true;
    }

    @Override
    protected Object getRequestBody(P param) {

        WebtRequest<P> request = new WebtRequest<>();
        WebtRequestSysHeader sysHeader = new WebtRequestSysHeader();
        sysHeader.setConsmMainSrlNo(getConsmMainSrlNo(param));
        sysHeader.setStdIntfcInd(this.path);
        sysHeader.setStdSvcInd(this.host);

        request.setSysHead(sysHeader);
        request.setBody(param);

        return JSON.toJSONString(request);
    }

    /**
     * 获取请求流水号
     * 
     * @param request
     * @return
     */
    protected String getConsmMainSrlNo(P param) {
        return HttpRequestUtil.getRequestNo();
    }

    @Override
    protected void init() {
        super.init();
        this.method = HttpMethod.POST;
    }
}
