package org.ilmostro.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.ThreadEntity;
import org.ilmostro.test.utils.InheritableUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei on 2019-10-21.
 * @description
 */
@RestController
@RequestMapping("/inheritable")
@Slf4j
public class InheritableController {

    @RequestMapping("/test")
    public ThreadEntity test(){
        ThreadEntity threadEntity = InheritableUtils.get();
        log.info("thread entity {}", threadEntity);
        return threadEntity;
    }

    @RequestMapping("/test1")
    public String test1(){
        return InheritableUtils.get().getSession();
    }
}
