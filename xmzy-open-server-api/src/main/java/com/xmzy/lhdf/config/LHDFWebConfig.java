package com.xmzy.lhdf.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xmzy.lhdf.resolver.UnderlineToCamelArgumentResolver;

/**
 * 添加配置
 *
 * @author L.D.J
 * @date 2021/09/23 10:38
 **/
@Configuration
public class LHDFWebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UnderlineToCamelArgumentResolver());
    }
}
