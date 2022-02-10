package org.ilmostro.pure.controller;

import org.ilmostro.pure.annotation.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@ApiVersion
@RequestMapping(value = "/api/{version}")
public class ApiVersionController {

    @GetMapping("/version")
    @ApiVersion(value = 3)
    public String version3(){
        return "3";
    }

    @GetMapping("/version")
    public String version1(){
        return "1";
    }
}
