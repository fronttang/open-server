package com.xmzy.lhdf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author L.D.J
 * @Description 参数类型装换注解，实现将下划线式参数转换为驼峰式命名参数
 * @Date 2021/9/23 10:22
 * @Param
 * @return
 */
@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterConvert {}