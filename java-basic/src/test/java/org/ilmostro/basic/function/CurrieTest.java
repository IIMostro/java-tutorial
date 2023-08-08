package org.ilmostro.basic.function;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

import java.util.function.Function;

/**
 * @author li.bowei
 * @date 2020/7/22 14:39
 */
@Slf4j
public class CurrieTest {

    @Test
    public void simple() {
        Function<Integer, Function<Integer, Function<Integer, Integer>>> currie = x -> y -> z -> x + y + z;
        Integer result = currie.apply(1).apply(2).apply(3);
    }

    @Test
    public void user_currie_builder() {
        final var zhangshan = builder.uid(1).username("zhangshan").age(2).build();
    }

    public static final UidInterface<Integer,
            UserNameInterface<String,
                    AgeInterface<Integer,
                            BuilderInterface<User>>>> builder =
            v1 -> v2 -> v3 -> () -> {
                final var user = new User();
                user.setId(v1);
                user.setName(v2);
                user.setAge(v3);
                return user;
            };


    @FunctionalInterface
    interface UidInterface<T, R> {
        R uid(T data);
    }

    @FunctionalInterface
    interface UserNameInterface<T, R> {
        R username(T data);
    }

    @FunctionalInterface
    interface AgeInterface<T, R> {
        R age(T data);
    }

    @FunctionalInterface
    interface BuilderInterface<R> {
        R build();
    }

}
