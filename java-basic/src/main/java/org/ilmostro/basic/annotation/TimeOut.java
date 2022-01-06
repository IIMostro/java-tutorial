package org.ilmostro.basic.annotation;

/**
 * @author li.bowei
 */
@SolveError
public @interface TimeOut {

    String value() default "";
}
