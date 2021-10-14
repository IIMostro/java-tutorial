package org.ilmostro.start.reflection;

import org.ilmostro.start.utils.ObjectMapperUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author li.bowei
 */
public class ReflectionUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtilsTest.class);

    @Test
    public void test(){

        List<Method> methods = new ArrayList<>();
        ReflectionUtils.doWithMethods(ObjectMapperUtils.class, var1 -> {
            var1.setAccessible(true);
            methods.add(var1);
        });

        for (Method method : methods) {
            logger.info("reflection name:{}", method.getName());
        }
    }
}
