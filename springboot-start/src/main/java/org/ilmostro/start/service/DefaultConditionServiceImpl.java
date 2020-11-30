package org.ilmostro.start.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/9/18 15:26
 */
@Service
@ConditionalOnSingleCandidate(ConditionService.class)
@Slf4j
public class DefaultConditionServiceImpl implements ConditionService {
    @Override
    public void say() {
        log.info("this is default condition service impl");
    }
}
