package io.github.wrobezin.framework.example.validator.config;

import io.github.wrobezin.framework.example.validator.interceptor.HeaderInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author yuan
 * date: 2019/12/16
 */
//@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {
    private final HeaderInterceptor handlerInterceptor;

    public InterceptorConfiguration(HeaderInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
