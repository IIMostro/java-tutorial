package org.ilmostro.start.annotation;

import java.lang.annotation.*;

/**
 * @author li.bowei
 * @date 2020/10/9 13:59
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface End {

    int value() default 0;
}
