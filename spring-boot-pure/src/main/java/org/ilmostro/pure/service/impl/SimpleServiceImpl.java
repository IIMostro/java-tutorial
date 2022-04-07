package org.ilmostro.pure.service.impl;

import org.ilmostro.pure.service.HelloService;
import org.ilmostro.pure.service.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class SimpleServiceImpl implements SimpleService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    private final HelloService service;

    public SimpleServiceImpl(HelloService service) {
        this.service = service;
    }

    @Override
    public void say(String message) {
        logger.info("this is simple service：{}", message);
    }

    @Override
    public String get(String message) {
        return message + service.hello();
    }
}
