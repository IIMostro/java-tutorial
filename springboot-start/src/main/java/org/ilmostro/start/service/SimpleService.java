package org.ilmostro.start.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/10/29 16:53
 */
@Service
@Slf4j
public class SimpleService {

    public void say(){
        log.info("this is simple service say()");
    }
}
