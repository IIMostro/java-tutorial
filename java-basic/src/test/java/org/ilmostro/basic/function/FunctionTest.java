package org.ilmostro.basic.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * @author li.bowei
 * @date 2020/7/23 9:45
 */
@Slf4j
public class FunctionTest {

    /**
     * 简单函数使用
     */
    @Test
    public void simple() {
        Function<String, String> function = String::toUpperCase;
        String name = "ilmostro";
        String result = function.apply(name);
        assertEquals(result, "ILMOSTRO");
        log.info("string to upper case function apply, param:{}, result:{}", name, result);
    }

    /**
     * 组合两个函数，compose是在这个函数之前执行
     */
    @Test
    public void compose() {
        Function<Long, Long> function = var1 -> var1 + 1;
        Function<Long, Long> before = var1 -> var1 * 2;
        Function<Long, Long> compose = function.compose(before);

        long number = 2L;
        long result = compose.apply(number);
        assertEquals(result, 5);
        log.info("function compose, number * 2 + 1, param:{}, result:{}", number, result);
    }

    /**
     * 组合两个函数， andThen是在这个函数之后执行的
     */
    @Test
    public void and() {
        Function<Long, Long> function = var1 -> var1 + 1;
        Function<Long, Long> after = var1 -> var1 * 2;
        Function<Long, Long> andThen = function.andThen(after);

        long number = 2L;
        long result = andThen.apply(number);
        assertEquals(result, 6);
        log.info("function andThen, (number + 1) * 2, param:{}, result:{}", number, result);
    }


    /**
     * 接受两个参数的函数
     */
    @Test
    public void biFunction(){
        BiFunction<String, Integer, String> function = String::substring;
        String str = "abcdefg";
        int sub = 3;
        String subString = function.apply(str, sub);
        log.info("bi function, string subString function, param:{} {}, result:{}",str, sub, subString);
        Function<String, Function<Integer, String>> equivalent = v1 -> v1::substring;
        String currie = equivalent.apply(str).apply(3);
        log.info("bi function equivalent currie function, result:{}", currie);
        assertEquals(subString, currie);
    }
}
