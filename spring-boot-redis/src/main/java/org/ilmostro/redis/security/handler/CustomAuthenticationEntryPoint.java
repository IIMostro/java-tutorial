package org.ilmostro.redis.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.dto.SimpleResponse;
import org.ilmostro.redis.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li.bowei
 **/
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("登录失败！");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(ObjectMapperUtils.toJSONString(new SimpleResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.getReasonPhrase())));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
