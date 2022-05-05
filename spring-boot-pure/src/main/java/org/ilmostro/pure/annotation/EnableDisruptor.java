package org.ilmostro.pure.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ilmostro.pure.configuration.DisruptorConfiguration;

import org.springframework.context.annotation.Import;

/**
 * @author li.bowei
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DisruptorConfiguration.class)
public @interface EnableDisruptor {
}
