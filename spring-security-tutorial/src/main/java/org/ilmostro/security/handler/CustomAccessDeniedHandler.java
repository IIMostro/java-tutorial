package org.ilmostro.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.security.dto.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.warn("this user can't access, cause:{}", e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new SimpleResponse<>(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.getReasonPhrase())));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
