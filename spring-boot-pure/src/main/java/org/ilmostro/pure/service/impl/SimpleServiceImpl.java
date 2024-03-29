package org.ilmostro.pure.service.impl;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.ilmostro.pure.annotation.Logger;
import org.ilmostro.pure.annotation.LoggerSupport;
import org.ilmostro.pure.service.HelloService;
import org.ilmostro.pure.service.SimpleService;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
@LoggerSupport
@Logger
public class SimpleServiceImpl implements SimpleService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    private final HelloService service;

    public SimpleServiceImpl(HelloService service) {
        this.service = service;
    }

    @Override
    public void say(String message) {
        logger.info("this is simple service：{}", message);
    }

    @Override
    @Counted
    @Timed
    public String get(String message) {
        return message + service.hello();
    }
}
