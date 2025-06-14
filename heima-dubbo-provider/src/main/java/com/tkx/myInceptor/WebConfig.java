package com.tkx.myInceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author tkx
 * @Date 2025 04 15 23 37
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Autowired
        private CustomInterceptor customInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 注册拦截器，并指定拦截的路径和排除的路径
            registry.addInterceptor(customInterceptor)
                    .addPathPatterns("/**") // 拦截所有请求
                    .excludePathPatterns("/login", "/public/**"); // 排除某些路径
        }
}
