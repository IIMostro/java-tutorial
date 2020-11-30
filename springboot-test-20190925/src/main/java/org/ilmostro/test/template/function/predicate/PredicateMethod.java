package org.ilmostro.test.template.function.predicate;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
public class PredicateMethod {

    /**
     * 判断是否为空
     *
     * @param <T> 参数
     * @return 函数
     */
    public <T extends Object> Predicate<T> getPredicate(){
        return Objects::isNull;
    }


    /**
     * 判断参数是否等于1
     *
     * @return 函数
     */
    public IntPredicate getIntPredicate(){
        return var1 -> var1 ==1;
    }
}
