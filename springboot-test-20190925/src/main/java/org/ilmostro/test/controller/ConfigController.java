package org.ilmostro.test.controller;

import org.ilmostro.test.configuration.RefreshProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei on 2019-10-09.
 * @description
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    private final RefreshProperties properties;

    @Autowired
    public ConfigController(RefreshProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/get")
    public String getConfig(){
        return properties.getName();
    }
}
