package com.xmzy.bank;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.constant.GHBProperties;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBController;
import com.xmzy.bank.ghb.GHBRequest;
import com.xmzy.bank.ghb.cipher.GHBRequestAesKeyCipher;
import com.xmzy.bank.ghb.cipher.GHBRequestCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;
import com.xmzy.bank.ghb.event.GHBRequestEvent;
import com.xmzy.bank.ghb.util.SecretKeyHolder;
import com.xmzy.base.sensitive.FastJsonSensitiveFilter;
import com.xmzy.base.util.XmzyAnnotationUtils;
import com.xmzy.server.base.context.RequestContextHolder;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 华兴银行请求报文处理
 * 
 * @author FrontTang
 * @date 2021/08/29
 */
@Slf4j
@RestControllerAdvice
@Profile({"test", "prod", "uat"})
public class GHBRequestBodyAdvice extends RequestBodyAdviceAdapter implements ApplicationEventPublisherAware {

    @Autowired
    private GHBProperties properties;

    private ApplicationEventPublisher publisher;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {

        // 只支持处理标记 了GHBController的 controller
        GHBController ghbController =
            XmzyAnnotationUtils.findAnnotationAndDeclaringAnnotation(methodParameter.getMethod(), GHBController.class);

        return ghbController != null;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        try {
            // 读取加密的请求体
            byte[] body = new byte[inputMessage.getBody().available()];
            inputMessage.getBody().read(body);

            GHBRequest request = JSON.parseObject(body, GHBRequest.class);

            log.info("请求数据:{}", JSON.toJSONString(request, new FastJsonSensitiveFilter()));

            RequestContextHolder current = (RequestContextHolder)RequestContextHolder.getCurrent();
            current.setItem(GHBConstants.REQUEST_HEADER_CONTEXT_KEY, request.getHeader());

            // 这里对request进行解密
            IGHBCipher<GHBRequest> cipher = getRequestCipher(request, parameter);
            request = cipher.decode(request);
            request.setApi(parameter.getMethod().toString());

            publisher.publishEvent(new GHBRequestEvent(request));

            body = ObjectUtil.toString(request.getBody()).getBytes();

            // 使用解密后的数据，构造新的读取流
            InputStream rawInputStream = new ByteArrayInputStream(body);
            HttpInputMessage httpInputMessage = new HttpInputMessage() {
                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }

                @Override
                public InputStream getBody() throws IOException {
                    return rawInputStream;
                }
            };

            return super.beforeBodyRead(httpInputMessage, parameter, targetType, converterType);
        } catch (GHBBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new GHBBusinessException(GHBResultCode.ERROR, e.getMessage());
        }
    }

    private IGHBCipher<GHBRequest> getRequestCipher(GHBRequest request, MethodParameter parameter) {
        GHBController ghbController =
            XmzyAnnotationUtils.findAnnotationAndDeclaringAnnotation(parameter.getMethod(), GHBController.class);

        Class<? extends IGHBCipher<?>> decoder = null;

        if (Objects.nonNull(ghbController)) {
            decoder = ghbController.decoder();
        }

        if (GHBRequestAesKeyCipher.class.isAssignableFrom(decoder)) {
            return new GHBRequestAesKeyCipher(properties.getPublicKey(), properties.getPrivateKey());
        }

        String secretKey = SecretKeyHolder.getSecretKey();

        if (StrUtil.isBlank(secretKey)) {
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        }

        RequestContextHolder current = (RequestContextHolder)RequestContextHolder.getCurrent();
        current.setItem(GHBConstants.SECRET_CONTEXT_KEY, secretKey);

        return new GHBRequestCipher(properties.getPublicKey(), properties.getPrivateKey(), secretKey);

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
