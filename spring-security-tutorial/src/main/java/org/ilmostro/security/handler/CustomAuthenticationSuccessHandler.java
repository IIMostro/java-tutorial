package org.ilmostro.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.security.domain.Role;
import org.ilmostro.security.domain.UserEntity;
import org.ilmostro.security.dto.SimpleResponse;
import org.ilmostro.security.dto.UserLoginResult;
import org.ilmostro.security.service.UserAuthenticationService;
import org.ilmostro.security.utils.JwtTokenUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 **/
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserAuthenticationService service;
    private final StringRedisTemplate stringRedisTemplate;

    public CustomAuthenticationSuccessHandler(UserAuthenticationService service,
                                              StringRedisTemplate stringRedisTemplate) {
        this.service = service;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User principal = (User) authentication.getPrincipal();
        UserEntity user = service.findUserByUserName(principal.getUsername());
//        List<String> roles = user.getRoles().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getUserName(), user.getId().toString(), roles, false);
        stringRedisTemplate.opsForValue().set(user.getId().toString(), token);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new SimpleResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new UserLoginResult(user, token))));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
