package org.ilmostro.pure.controller;

import org.ilmostro.pure.annotation.ApiVersion;
import org.ilmostro.pure.support.WebConfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@ApiVersion
@RequestMapping(value = "/api/{version}")
@ConditionalOnBean(WebConfiguration.class)
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
