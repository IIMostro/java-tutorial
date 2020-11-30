package org.ilmostro.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei on 2019-10-29.
 * @description consul健康检查
 */
@RestController
public class HealthCheck {

    @GetMapping("/info.json")
    @ResponseStatus(HttpStatus.OK)
    public String check(){
        return "OK";
    }
}
