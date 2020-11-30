package org.ilmostro.start.service;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.annotation.Environment;
import org.ilmostro.start.utils.ThreadLocalUtils;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/10/29 16:10
 */
@Service
@Slf4j
public class DefaultServiceImpl implements AspectService {

    @Override
    @Environment
    public String say() {
        log.info("service value:{}", ThreadLocalUtils.get());
        return "OK";
    }

    public String simple(){
        log.info("simple value:{}",ThreadLocalUtils.get());
        return "OK";
    }
}
