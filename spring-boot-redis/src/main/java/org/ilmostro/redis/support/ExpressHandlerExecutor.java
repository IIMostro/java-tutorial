package org.ilmostro.redis.support;

import java.lang.reflect.Method;

/**
 * @author li.bowei
 */
public interface ExpressHandlerExecutor {

    /**
     * SPEl表达式解析
     *
     * @param express 表达式内容
     * @param method  方法
     * @param args    方法参数
     * @return 解析内容
     */
    String parse(String express, Method method, Object[] args);
}
