package org.ilmostro.redis.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author li.bowei
 **/
@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final StringRedisTemplate stringRedisTemplate;

    public CustomLogoutSuccessHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("登出成功");
        if(Objects.isNull(authentication)){
            return;
        }
        String name = authentication.getName();
        stringRedisTemplate.delete(name);
    }
}
