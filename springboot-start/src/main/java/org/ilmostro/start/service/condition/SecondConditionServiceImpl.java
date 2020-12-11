package org.ilmostro.start.service.condition;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.service.condition.ConditionService;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/9/18 15:28
 */
@Service
@Slf4j
public class SecondConditionServiceImpl implements ConditionService {
    @Override
    public void say() {
        log.info("this is second condition service impl");
    }
}
