package org.ilmostro.pure.support;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author li.bowei
 */
@Configuration
public class WebConfiguration implements WebMvcRegistrations {

    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        CustomRequestHandlerMapping mapping = new CustomRequestHandlerMapping();
        mapping.setOrder(0);
        return mapping;
    }

}
