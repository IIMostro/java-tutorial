package org.ilmostro.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.service.generic.GenericTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.GenericTypeResolver;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class GenericTypeResolverUtilsTest {

    @Autowired
    private List<GenericTypeService<?>> services;

    @Test
    public void test(){
        for (GenericTypeService<?> service : services) {
            //noinspection rawtypes
            Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(service.getClass());
            log.info("service:[{}], type variable:[{}]", service.toString(), typeVariableMap);
        }
    }

}