package com.uom.idecide.config;

import com.uom.idecide.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * This is a configuration class which likes a registry.
 * We can register our custom interceptors here and
 * so that springboot will pass all the request through
 * the registered interceptors first.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired  //inject our custom interceptor here
    JwtInterceptor jwtInterceptor;

    //overwrite addInterceptors() in WebMvcConfigurationSupport to
    protected void addInterceptors(InterceptorRegistry registry){
        //register the interceptor
        registry.addInterceptor(jwtInterceptor)
                //intercept all kinds of path
                .addPathPatterns("/**")
                //except login path
                .excludePathPatterns("/**/login/**");
    }

}
