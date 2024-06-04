/**
 * 
 */
package com.xmzy.bank.ghb;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.RestController;

import com.xmzy.bank.ghb.cipher.GHBRequestCipher;
import com.xmzy.bank.ghb.cipher.GHBResponseCipher;
import com.xmzy.bank.ghb.cipher.IGHBCipher;

/**
 * 用于标记这是一个华兴银行controller,会进行加解密处理
 * 
 * @author fronttang
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@RestController
public @interface GHBController {

    /**
     * 解密处理类
     * 
     * @return
     */
    Class<? extends IGHBCipher<GHBRequest>> decoder() default GHBRequestCipher.class;

    /**
     * 加密处理类
     * 
     * @return
     */
    Class<? extends IGHBCipher<GHBResponse>> encoder() default GHBResponseCipher.class;
}
