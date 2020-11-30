package org.ilmostro.test.template.function.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class FunctionMethodTest {

    @Test
    public void getFunction() {
        Function<Double, BigDecimal> function = new FunctionMethod().getFunction();
        Double data = 0.1d;
        log.info("function apply param is {}, calculate is {}", data, function.apply(data));
    }

    @Test
    public void getBiFunction(){
        BiFunction<Double, Double, BigDecimal> function = new FunctionMethod().getBiFunction();
        double var1 = 1.0;
        double var2 = 2.0;
        BigDecimal apply = function.apply(var1, var2);
        log.info("bi function apply var1={}, var2={}, sum={}", var1, var2, apply);
    }

    @Test
    public void getDoubleFunction(){
        DoubleFunction<BigDecimal> function = new FunctionMethod().getDoubleFunction();
        double var1 = 1.0;
        BigDecimal apply = function.apply(var1);
        log.info("double function apply, var1={}, var1+1={}", var1, apply);
    }
}