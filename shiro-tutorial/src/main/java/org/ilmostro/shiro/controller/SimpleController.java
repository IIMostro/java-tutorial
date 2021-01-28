package org.ilmostro.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ilmostro.shiro.configuration.ShiroCacheConstant;
import org.ilmostro.shiro.domain.UserEntity;
import org.ilmostro.shiro.dto.BasicResult;
import org.ilmostro.shiro.entity.LoginParameter;
import org.ilmostro.shiro.services.UserAuthenticationService;
import org.ilmostro.shiro.utils.JwtTokenUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

/**
 * @author li.bowei
 **/
@RestController
@RequestMapping("/")
public class SimpleController {

    private final UserAuthenticationService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final StringRedisTemplate template;

    public SimpleController(UserAuthenticationService service,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            StringRedisTemplate template) {
        this.service = service;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.template = template;
    }

    @PostMapping("/login")
    public BasicResult<String> login(@RequestBody LoginParameter parameter){
        UserEntity user = service.findUserByUserName(parameter.getUsername());
        if(Objects.isNull(user)){
            throw new AuthenticationException("user not exist!");
        }

        Boolean password = Optional.of(parameter)
                .map(LoginParameter::getPassword)
                .map(var1 -> bCryptPasswordEncoder.matches(var1, user.getPassword()))
                .orElse(false);
        if(!password){
            throw new AuthenticationException("password is error!");
        }

        String token = JwtTokenUtils.sign(user.getUserName(), user.getId(), false);
        template.opsForValue().set(ShiroCacheConstant.TOKEN_CACHE_KEY + user.getId(), token);
        return BasicResult.success(token);
    }

    @GetMapping("/simple")
    @RequiresPermissions("ADMIN")
    public BasicResult<String> simple(){
        return BasicResult.success("this is simple url");
    }
}
