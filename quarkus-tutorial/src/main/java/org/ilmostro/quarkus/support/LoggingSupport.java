package org.ilmostro.quarkus.support;

import org.ilmostro.quarkus.annotation.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logging
@Interceptor
public class LoggingSupport {

    private final Logger logger = LoggerFactory.getLogger(LoggingSupport.class);

    @AroundInvoke
    public Object execution(InvocationContext context) throws Exception {
        logger.info("service into invoke!");
        Object proceed = context.proceed();
        logger.info("service process done!");
        return proceed;
    }
}
