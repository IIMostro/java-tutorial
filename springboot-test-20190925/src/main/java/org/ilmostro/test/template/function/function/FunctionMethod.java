package org.ilmostro.test.template.function.function;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

/**
 * @author li.bowei on 2019-10-12.
 * @description function函数
 */
public class FunctionMethod {

    /**
     * 将double类型转换成BigDecimal
     *
     * @return function函数
     */
    public Function<Double, BigDecimal> getFunction(){
        return BigDecimal::valueOf;
    }

    /**
     * 两个参数返回一个值
     *
     * @param <T> 参数1
     * @param <U> 参数2
     * @param <R> 计算值
     * @return T+U
     */
    public <T extends Double, U extends Double, R extends BigDecimal> BiFunction<T,U,R> getBiFunction(){
        //noinspection unchecked
        return (t, u) -> (R) BigDecimal.valueOf(t).add(BigDecimal.valueOf(u));
    }

    /**
     * 传入一个Double类型的参数，返回一个BigDecimal+1
     *
     * @return 返回BigDecimal类型的double + 1
     */
    public DoubleFunction<BigDecimal> getDoubleFunction(){
        return value -> BigDecimal.ONE.add(BigDecimal.valueOf(value));
    }
}
