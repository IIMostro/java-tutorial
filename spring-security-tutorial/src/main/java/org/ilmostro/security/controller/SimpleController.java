package org.ilmostro.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.security.domain.UserEntity;
import org.ilmostro.security.service.UserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 **/
@RestController
@Slf4j
public class SimpleController {

    private final UserAuthenticationService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SimpleController(UserAuthenticationService userService,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/admin/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String admin() {
        return "this url is /admin/create";
    }

    @GetMapping("/api/create")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String api() {
        UserEntity currentUser = userService.getCurrentUser();
        log.info("get current user:{}", currentUser);
        return "this url is /api/create";
    }

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
