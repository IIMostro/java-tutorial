package org.ilmostro.test.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.ThreadEntity;
import org.ilmostro.test.utils.InheritableUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li.bowei on 2019-10-21.
 * @description
 */
@Slf4j
public class InheritableInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadEntity var1 = ThreadEntity.builder()
                .session(request.getSession().getId())
                .threadId(Thread.currentThread().getId())
                .threadName(Thread.currentThread().getName())
                .build();
        InheritableUtils.set(var1);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("interceptor after remove inheritable");
        InheritableUtils.remove();
    }
}
