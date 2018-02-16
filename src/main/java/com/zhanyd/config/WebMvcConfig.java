package com.zhanyd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zhanyd.common.interceptor.PermissionInterceptor;

/**
 * Created by zhanyd@sina.com on 2018/2/16 0016.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 添加过滤链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/sendIdentifyingCode");
    }
}
