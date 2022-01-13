package org.ilmostro.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * key prefix
     *
     * @return key prefix
     */
    String prefix() default "";

    /**
     * 锁key
     *
     * @return key
     */
    String key();

    /**
     * 锁的时间
     *
     * @return 锁时间
     */
    long time() default 60;

    /**
     * 锁的时间单位
     *
     * @return 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 公平锁
     *
     * @return 是否公平锁
     */
    boolean fair() default false;

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
