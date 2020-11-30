package org.ilmostro.test.configuration;

import org.ilmostro.test.Interceptor.InheritableInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author li.bowei on 2019-10-21.
 * @description
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInheritableInterceptor()).addPathPatterns("/inheritable/**");
    }

    @Bean
    public InheritableInterceptor getInheritableInterceptor(){
        return new InheritableInterceptor();
    }
}
