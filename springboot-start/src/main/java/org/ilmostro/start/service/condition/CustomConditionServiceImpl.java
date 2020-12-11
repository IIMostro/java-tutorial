package org.ilmostro.start.service.condition;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 * @date 2020/10/9 14:01
 */
@Slf4j
public class CustomConditionServiceImpl implements CustomConditionService {
    @Override
    public void say() {
        log.info("this is CustomConditionServiceImpl say()");
    }
}
