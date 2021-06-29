package org.ilmostro.test.service.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class StringGenericTypeServiceImpl implements GenericTypeService<String>{
    @Override
    public void test(String data) {
        log.info("this is string generic type service: [data: {}]", data);
    }

    @Override
    public String toString() {
        return "StringGenericTypeServiceImpl";
    }
}
