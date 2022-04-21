package org.ilmostro.mysql.shiro.controller;

import java.util.Objects;
import java.util.Optional;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ilmostro.mysql.shiro.configuration.ShiroCacheConstant;
import org.ilmostro.mysql.shiro.domain.UserEntity;
import org.ilmostro.mysql.shiro.dto.BasicResult;
import org.ilmostro.mysql.shiro.entity.LoginParameter;
import org.ilmostro.mysql.shiro.services.UserAuthenticationService;
import org.ilmostro.mysql.shiro.utils.JwtTokenUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 **/
@RestController
@RequestMapping("/")
public class SimpleController {

    private final UserAuthenticationService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SimpleController(UserAuthenticationService service,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.service = service;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
//        template.opsForValue().set(ShiroCacheConstant.TOKEN_CACHE_KEY + user.getId(), token);
        return BasicResult.success(token);
    }

    @GetMapping("/simple")
    @RequiresPermissions("ADMIN")
    public BasicResult<String> simple(){
        return BasicResult.success("this is simple url");
    }
}
