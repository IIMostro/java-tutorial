package org.ilmostro.start.annotation;

import java.lang.annotation.*;

/**
 * @author li.bowei
 * @date 2020/10/29 16:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Environment {
}
