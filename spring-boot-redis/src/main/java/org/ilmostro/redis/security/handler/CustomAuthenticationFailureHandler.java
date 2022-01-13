package org.ilmostro.redis.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.dto.SimpleResponse;
import org.ilmostro.redis.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li.bowei
 **/
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info("登录失败！");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(ObjectMapperUtils.toJSONString(new SimpleResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null)));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
