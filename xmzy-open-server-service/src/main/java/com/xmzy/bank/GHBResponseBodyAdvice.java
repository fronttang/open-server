package com.xmzy.bank;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.xmzy.bank.constant.GHBConstants;
import com.xmzy.bank.constant.GHBProperties;
import com.xmzy.bank.enums.GHBResultEnum;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.bank.ghb.GHBController;
import com.xmzy.bank.ghb.GHBRequestHeader;
import com.xmzy.bank.ghb.GHBResponse;
import com.xmzy.bank.ghb.GHBResponseHeader;
import com.xmzy.bank.ghb.cipher.GHBResponseAesKeyCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;
import com.xmzy.bank.ghb.event.GHBResponseEvent;
import com.xmzy.bank.ghb.util.SecretKeyHolder;
import com.xmzy.base.util.XmzyAnnotationUtils;
import com.xmzy.server.base.context.IRequestContext;
import com.xmzy.server.base.context.RequestContextHolder;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 华兴银行响应报文处理
 * 
 * @author FrontTang
 * @date 2021/08/29
 */
// @Slf4j
@RestControllerAdvice
@Profile({"test", "prod", "uat"})
public class GHBResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice
    implements ApplicationEventPublisherAware {

    @Autowired
    private GHBProperties properties;

    private ApplicationEventPublisher publisher;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        // 只支持处理标记 了GHBController的 controller
        GHBController ghbController =
            XmzyAnnotationUtils.findAnnotationAndDeclaringAnnotation(returnType.getMethod(), GHBController.class);

        return ghbController != null;
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
        MethodParameter returnType, ServerHttpRequest req, ServerHttpResponse resp) {

        GHBResponse response = null;
        try {

            Object body = bodyContainer.getValue();

            if (body instanceof GHBResponse) {
                // 如果返回的 是GHBResponse对象，直接加密返回到前端
                // 如果不是，则当做body进行返回
                response = (GHBResponse)body;

                GHBResponseHeader header = response.getHeader();
                header = buildResponseHeader(header);
                response.setHeader(header);

            } else {
                response = buildResponse(response, body, GHBResultEnum.SUCCESS.errorCode(),
                    GHBResultEnum.SUCCESS.getErrorMsg());
            }

            // 这里对response进行加密
            IGHBCipher<GHBResponse> cipher = getResponseCipher(response, returnType);

            publisher.publishEvent(new GHBResponseEvent(response));

            response = cipher.encode(response);

        } catch (GHBBusinessException e) {
            response = buildResponse(response, "{}", e.errorCode(), e.getMessage());
        } catch (Exception e) {
            response = buildResponse(response, "{}", GHBResultEnum.BUSINESS_ERROR.errorCode(), e.getMessage());
        } finally {

            if (Objects.isNull(response)) {
                response = buildResponse(response, "{}", GHBResultEnum.BUSINESS_ERROR.errorCode(),
                    GHBResultEnum.BUSINESS_ERROR.getErrorMsg());
            }

            // 返回结果日志打印
            // log.info("响应数据:{}", JSON.toJSONString(response, new FastJsonSensitiveFilter()));
            bodyContainer.setValue(response);
        }

    }

    private GHBResponse buildResponse(GHBResponse response, Object body, String errorCode, String errorMsg) {

        if (Objects.isNull(response)) {
            response = new GHBResponse();
            GHBResponseHeader responseHeader = new GHBResponseHeader();
            responseHeader.setErrorCode(errorCode);
            responseHeader.setErrorMsg(errorMsg);
            response.setHeader(responseHeader);
        }

        GHBResponseHeader responseHeader = response.getHeader();
        responseHeader = buildResponseHeader(responseHeader);

        response.setHeader(responseHeader);
        response.setBody(body);

        return response;
    }

    private GHBResponseHeader buildResponseHeader(GHBResponseHeader responseHeader) {

        if (Objects.isNull(responseHeader)) {
            responseHeader = new GHBResponseHeader();
        }

        IRequestContext current = RequestContextHolder.getCurrent();

        GHBRequestHeader requestHeader = (GHBRequestHeader)current.getItem(GHBConstants.REQUEST_HEADER_CONTEXT_KEY);
        if (Objects.nonNull(requestHeader)) {
            responseHeader.setAppId(requestHeader.getAppId());
            responseHeader.setRequestId(requestHeader.getRequestId());
        }

        // TODO 先每次调用都生成
        LocalDateTime now = LocalDateTime.now();
        String responseTime = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
        String responseId = responseTime + RandomUtil.randomNumbers(6);

        responseHeader.setResponseId(responseId);
        responseHeader.setResponseTime(responseTime);

        return responseHeader;
    }

    private IGHBCipher<GHBResponse> getResponseCipher(GHBResponse response, MethodParameter parameter) {

        GHBController ghbController =
            XmzyAnnotationUtils.findAnnotationAndDeclaringAnnotation(parameter.getMethod(), GHBController.class);

        Class<? extends IGHBCipher<?>> encoder = null;

        if (Objects.nonNull(ghbController)) {
            encoder = ghbController.encoder();
        }

        if (GHBResponseAesKeyCipher.class.isAssignableFrom(encoder)) {
            return new GHBResponseAesKeyCipher(properties.getPublicKey(), properties.getPrivateKey());
        }

        // 从上下文中拿KEY
        IRequestContext current = RequestContextHolder.getCurrent();
        String secretKey = (String)current.getItem(GHBConstants.SECRET_CONTEXT_KEY);

        if (StrUtil.isBlank(secretKey)) {
            // 没拿到就从redis里面拿，
            secretKey = SecretKeyHolder.getSecretKey();
        }

        // 还是拿不到就报错
        if (StrUtil.isBlank(secretKey)) {
            throw new GHBBusinessException(GHBResultEnum.SECRET_KEY_ERROR);
        }

        return new GHBResponseCipher(properties.getPublicKey(), properties.getPrivateKey(), secretKey);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
