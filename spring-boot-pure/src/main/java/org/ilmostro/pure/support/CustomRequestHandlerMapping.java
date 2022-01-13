package org.ilmostro.pure.support;


import org.ilmostro.pure.annotation.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author li.bowei
 */
public class CustomRequestHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(@NonNull Class<?> handlerType) {
        ApiVersion annotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return create(annotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(@NonNull Method method) {
        ApiVersion annotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return create(annotation);
    }

    private static RequestCondition<ApiVersionCondition> create(ApiVersion annotation) {
        return annotation == null ? null : new ApiVersionCondition(annotation.value());
    }

}
