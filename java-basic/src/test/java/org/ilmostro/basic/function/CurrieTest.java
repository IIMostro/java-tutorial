package org.ilmostro.basic.function;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * @author li.bowei
 * @date 2020/7/22 14:39
 */
@Slf4j
public class CurrieTest {

    @Test
    public void simple(){
        Function<Integer, Function<Integer, Function<Integer, Integer>>> currie = x -> y -> z -> x + y + z;
        Integer result = currie.apply(1).apply(2).apply(3);
    }
}
