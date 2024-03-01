package org.ilmostro.pure.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodAdapterSupport {

    @Around("@annotation(adapter)")
    public Object Around(ProceedingJoinPoint pjp, MethodAdapter adapter) throws Throwable{
        return pjp.proceed();
    }
}
