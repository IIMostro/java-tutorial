package org.ilmostro.start.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.ilmostro.start.utils.ThreadLocalUtils;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 * @date 2020/10/29 16:01
 */
@Aspect
@Component
@Slf4j
public class StartAspect {

//    @Pointcut("execution(* org.ilmostro.start.service.AspectService.*(..))")
//    public void pointcut(){
//    }

    @Pointcut("@annotation(org.ilmostro.start.annotation.Environment)")
    public void pointcut(){

    }

    @Before(value = "pointcut()")
    public void init(){
        ThreadLocalUtils.set(11111);
    }

    @After(value = "pointcut()")
    public void destroy(){
        log.info("aspect after value:{}", ThreadLocalUtils.get());
        ThreadLocalUtils.clean();
    }
}
