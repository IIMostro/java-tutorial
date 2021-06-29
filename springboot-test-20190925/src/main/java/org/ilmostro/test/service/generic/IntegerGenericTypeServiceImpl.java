package org.ilmostro.test.service.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class IntegerGenericTypeServiceImpl implements GenericTypeService<Integer>{
    @Override
    public void test(Integer data) {
        log.info("this is integer generic type service: [data: {}]", data);
    }

    @Override
    public String toString() {
        return "IntegerGenericTypeServiceImpl";
    }
}
