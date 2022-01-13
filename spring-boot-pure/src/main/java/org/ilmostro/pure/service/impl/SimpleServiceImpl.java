package org.ilmostro.pure.service.impl;

import org.ilmostro.pure.service.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimpleServiceImpl implements SimpleService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

    @Override
    public void say(String message) {
        logger.info("this is simple service：{}", message);
    }
}
