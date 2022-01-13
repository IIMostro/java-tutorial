package org.ilmostro.pure.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author li.bowei
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NettyHandler{

    String path();

    String name() default "";
}
