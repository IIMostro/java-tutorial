package org.ilmostro.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.security.domain.UserEntity;
import org.ilmostro.security.dto.SimpleResponse;
import org.ilmostro.security.dto.UserLoginResult;
import org.ilmostro.security.entity.UserLoginParameter;
import org.ilmostro.security.service.UserAuthenticationService;
import org.ilmostro.security.utils.JwtTokenUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 **/
@RestController
@Slf4j
public class SimpleController {

    private final UserAuthenticationService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    public SimpleController(UserAuthenticationService userService,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/admin/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String admin() {
        return "this url is /admin/create";
    }

    @GetMapping("/api/create")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String api() {
        UserEntity currentUser = userService.getCurrentUser();
        log.info("get current user:{}", currentUser);
        return "this url is /api/create";
    }

//    @PostMapping("/login")
//    public SimpleResponse<UserLoginResult> login(@RequestBody UserLoginParameter parameter) {
//
//        return SimpleResponse.success(new UserLoginResult(user, token));
//    }

    @GetMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(username);
        String encode = bCryptPasswordEncoder.encode(password);
        userEntity.setPassword(encode);
        userEntity.setFullName(username);
        userService.save(userEntity);
        return HttpStatus.OK.getReasonPhrase();
    }
}
