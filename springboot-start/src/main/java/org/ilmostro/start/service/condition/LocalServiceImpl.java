package org.ilmostro.start.service.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/7/16 9:34
 */
@Slf4j
@Service
@Primary
@ConditionalOnProperty(value = "start.active.source", havingValue = "local", matchIfMissing = true)
public class LocalServiceImpl implements ActiveService{
    @Override
    public String execute() {
        log.info("this is local service");
        return "this is local service";
    }
}
