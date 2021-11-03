package org.ilmostro.start.service.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class StringGenericServiceImpl implements GenericService<String>{

    @Override
    public void say(String data) {
        log.info(data);
    }
}
