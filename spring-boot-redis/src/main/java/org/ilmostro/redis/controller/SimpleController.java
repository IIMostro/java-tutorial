package org.ilmostro.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@RequestMapping("/redis")
public class SimpleController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
