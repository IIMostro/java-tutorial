package org.ilmostro.pure.support;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author li.bowei
 */
@Configuration
public class WebConfiguration extends WebMvcAutoConfiguration {

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        CustomRequestHandlerMapping mapping = new CustomRequestHandlerMapping();
        mapping.setOrder(0);
        return mapping;
    }

}
