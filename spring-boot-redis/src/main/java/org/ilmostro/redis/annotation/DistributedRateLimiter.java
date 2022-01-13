package org.ilmostro.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedRateLimiter{

    /**
     * 前缀
     *
     * @return 限流器名称前缀
     */
    String prefix() default "";

    /**
     * key
     *
     * @return 限流器key
     */
    String key() default "";

    /**
     * 时间窗口
     *
     * @return 1
     */
    long time() default 1;

    /**
     * 时间窗口时间单位
     *
     * @return 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 时间窗口内可以请求的次数
     *
     * @return 请求次数
     */
    long max() default 10;

    /**
     * 错误状态下的处理类, 可以是spring容器中的类
     *
     * @return 错误处理类
     */
    Class<?> exceptionHandlerClass() default Object.class;

    /**
     * 默认处理方法
     * 如果exceptionHandlerClass为Object ,返回本类下的method执行方法， 必须为public，并且参数与执行的一致
     * 如果exceptionHandlerClass为别的，则exceptionHandler必须为public和static, 参数与执行的一致
     *
     * @return 错误处理方法
     */
    String exceptionHandler() default "";
}
