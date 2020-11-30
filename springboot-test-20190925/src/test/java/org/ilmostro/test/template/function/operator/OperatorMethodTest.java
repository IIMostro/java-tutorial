package org.ilmostro.test.template.function.operator;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.BinaryOperator;

import static org.junit.Assert.assertEquals;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class OperatorMethodTest {

    @Test
    public void getBinaryOperator() {
        BinaryOperator<String> function = new OperatorMethod().getBinaryOperator();
        String var1 = "hello";
        String var2 = "world";
        String apply = function.apply(var1, var2);
        log.info("var1={}, var2={}, apply={}", var1, var2, apply);
        assertEquals(var1+var2, apply);
    }
}