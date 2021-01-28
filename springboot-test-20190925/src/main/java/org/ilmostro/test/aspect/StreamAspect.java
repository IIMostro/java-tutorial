package org.ilmostro.test.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei on 2019-09-26.
 * @description
 */
@Component
@Aspect
@Slf4j
public class StreamAspect {

    @Pointcut("execution(* org.ilmostro.test.service.autowired.StreamService.*(..))")
    public void ponitcut(){

    }

    @Around("ponitcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String name = joinPoint.getSignature().getName();
        Object proceed = joinPoint.proceed();
        log.info("method {}, expend {}", name, System.currentTimeMillis() - start);
        return proceed;
    }
}
