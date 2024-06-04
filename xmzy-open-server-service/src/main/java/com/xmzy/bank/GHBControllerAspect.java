package com.xmzy.bank;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xmzy.bank.constant.GHBResultCode;
import com.xmzy.bank.exception.GHBBusinessException;
import com.xmzy.base.sensitive.FastJsonSensitiveFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 对华兴银行接口的拦截处理
 * 
 * @author FrontTang
 * @date 2021/08/29
 */
@Aspect
@Component
@Slf4j
public class GHBControllerAspect {

    @Pointcut("@annotation(com.xmzy.bank.ghb.GHBController) || @within(com.xmzy.bank.ghb.GHBController)")
    public void ghbController() {}

    /**
     * 
     */
    @AfterThrowing(pointcut = "ghbController()", throwing = "ex")
    public void doAfterThrowing(Throwable ex) {

        if (Objects.nonNull(ex)) {
            if (ex instanceof GHBBusinessException) {

            } else {
                // 发生了异常全部转成银行业务错误
                throw new GHBBusinessException(GHBResultCode.ERROR, ex.getMessage());
            }
        }
    }

    /**
     * 接口返回结果记录日志
     */
    @AfterReturning(pointcut = "ghbController()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("返回数据:{}", JSON.toJSONString(result, new FastJsonSensitiveFilter()));
    }

}
