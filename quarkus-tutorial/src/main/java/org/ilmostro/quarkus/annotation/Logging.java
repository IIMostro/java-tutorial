package org.ilmostro.quarkus.annotation;


import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {
}
