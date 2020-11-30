package org.ilmostro.start.service;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.annotation.End;

/**
 * @author li.bowei
 * @date 2020/10/9 14:02
 */
@Slf4j
@End
public class CustomConditionServiceImpl1 implements CustomConditionService{
    @Override
    public void say() {
        log.info("this is CustomConditionServiceImpl1 say()");
    }
}
