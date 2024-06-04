/**
 * 
 */
package com.xmzy.bank.ghb;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Indexed;

import com.xmzy.api.annotation.Api;

/**
 * 用于标记这是一个银行api
 * 
 * @author fronttang
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@Api
public @interface GHBApi {

    /**
     * API HOST
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String host() default "";

    /**
     * API 路径
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String path() default "";

    /**
     * API 编号
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String code() default "";

    /**
     * API名称
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String name() default "";

    /**
     * API 请求方式
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    HttpMethod method() default HttpMethod.POST;
}
