package com.free.badmood.blackhole.config;

import com.free.badmood.blackhole.filter.AuthenticationInterceptor;
import com.free.badmood.blackhole.filter.WebInterfaceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private WebInterfaceInterceptor webInterfaceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //order 谁大先走谁
//        registry.addWebRequestInterceptor(webInterfaceInterceptor).addPathPatterns("/support/add").order(2);
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**").order(1);
    }
}
