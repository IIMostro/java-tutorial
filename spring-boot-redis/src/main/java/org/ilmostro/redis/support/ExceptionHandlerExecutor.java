package org.ilmostro.redis.support;

import java.lang.reflect.Method;

/**
 * @author li.bowei
 */
public interface ExceptionHandlerExecutor {

    /**
     * 错误的处理
     *
     * @param target           错误执行的类
     * @param targetMethod     错误执行的方法
     * @param targetArgs       错误执行的参数
     * @param exceptionClass   降级执行的类
     * @param exceptionHandler 降级执行的方法
     * @return 降级执行的结果
     * @throws Exception 反射错误
     */
    Object exceptionHandler(Object target, Method targetMethod, Object[] targetArgs,
                            Class<?> exceptionClass, String exceptionHandler) throws Exception;
}
