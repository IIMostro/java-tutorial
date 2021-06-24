package org.ilmostro.start.aspect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
public abstract class AbstractExceptionHandlerSupport implements ApplicationContextAware,
        ExceptionHandlerExecutor, ExpressHandlerExecutor {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractExceptionHandlerSupport.class);
    protected static ApplicationContext context;

    public Object exceptionHandler(Object target, Method targetMethod, Object[] targetArgs,
                                   Class<?> exceptionClass, String exceptionHandler) throws Exception {
        logger.debug("exception handler executor:[class :{} executor error, method:{}, " +
                        "exception executor clazz:{}, handler:{}]", target.getClass().getSimpleName(),
                targetMethod.getName(), exceptionClass.getSimpleName(), exceptionHandler);
        //否则返回本类下的method执行方法， 必须为public，并且参数与执行的一致
        Class<?>[] classes = Optional.ofNullable(targetArgs)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .map(Object::getClass).toArray(Class<?>[]::new);
        //如果是默认的exceptionHandlerClass
        if (exceptionClass.getName().equals("java.lang.Object") || exceptionClass.equals(target.getClass())) {
            //如果是默认的exceptionHandler，直接返回空
            if (StringUtils.isBlank(exceptionHandler)) {
                return null;
            }
            //找到本类中的方法
            Method method = target.getClass().getMethod(exceptionHandler, classes);
            return method.invoke(target, targetArgs);
        } else {
            Object bean = null;
            try {
                //首先从spring容器中获取是否有bean， 如果有bean的就执行方法返回
                bean = context.getBean(exceptionClass);
            } catch (Exception ignored) {
            }
            if (Objects.nonNull(bean)) {
                return bean.getClass().getMethod(exceptionHandler, classes).invoke(bean, targetArgs);
            }
            //否则就执行指定的静态方法
            Class<?> threadClazz = Class.forName(exceptionClass.getName());
            Method method = threadClazz.getMethod(exceptionHandler, classes);
            return method.invoke(null, targetArgs);
        }
    }

    @Override
    public String parse(String express, Method method, Object[] args) {
        if(StringUtils.isBlank(express)){
            return "";
        }
        //获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNameArr = discoverer.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (Objects.nonNull(paramNameArr) && paramNameArr.length > 0) {
            for (int i = 0; i < paramNameArr.length; i++) {
                context.setVariable(paramNameArr[i], args[i]);
            }
        }
        Expression expression = parser.parseExpression(express);
        return expression.getValue(context, String.class);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
