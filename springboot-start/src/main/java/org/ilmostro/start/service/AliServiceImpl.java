package org.ilmostro.start.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/7/16 9:31
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "start.active.source", havingValue = "ali")
public class AliServiceImpl implements ActiveService{
    @Override
    public String execute() {
        log.info("this is ali server");
        return "this is ali server";
    }
}
