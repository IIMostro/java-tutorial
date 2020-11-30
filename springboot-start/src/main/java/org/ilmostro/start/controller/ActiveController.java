package org.ilmostro.start.controller;

import org.ilmostro.start.service.ActiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 * @date 2020/7/16 9:35
 */
@RestController
@RequestMapping("/active")
public class ActiveController {

    private final ActiveService service;

    public ActiveController(ActiveService service) {
        this.service = service;
    }

    @GetMapping
    public String test(){
        return service.execute();
    }
}
