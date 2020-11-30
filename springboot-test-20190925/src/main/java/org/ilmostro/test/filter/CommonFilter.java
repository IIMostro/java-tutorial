package org.ilmostro.test.filter;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.ThreadEntity;
import org.ilmostro.test.utils.InheritableUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author li.bowei on 2019-10-21.
 * @description
 */
//@Component
@Slf4j
public class CommonFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init this filter");
    }

    @Override
    public void destroy() {
        log.info("destroy filter");
        InheritableUtils.remove();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("do filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ThreadEntity var1 = ThreadEntity.builder()
                .session(httpServletRequest.getSession().getId())
                .threadId(Thread.currentThread().getId())
                .threadName(Thread.currentThread().getName())
                .build();
        InheritableUtils.set(var1);
        chain.doFilter(request, response);
    }
}
