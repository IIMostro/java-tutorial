package org.ilmostro.vertx.service.impl;

import org.ilmostro.vertx.service.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Service
public class SimpleServiceImpl implements SimpleService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    @Override
    public void say(String message) {
        logger.info("this is simple service：{}", message);
    }
}
