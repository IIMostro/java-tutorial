package org.ilmostro.test.controller;

import org.ilmostro.test.service.autowired.BeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei on 2019-09-25.
 * @description
 */
@RestController
public class BeanController {

    @Autowired
    private BeanService service;

    @GetMapping("/set")
    public void set(String name){
        service.set(name);
    }

    @GetMapping("/get")
    public String get(){
        return service.get();
    }

}
