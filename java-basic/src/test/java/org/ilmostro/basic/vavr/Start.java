package org.ilmostro.basic.vavr;

import io.vavr.API;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

import static io.vavr.API.$;
import static io.vavr.API.Case;

@Slf4j
public class Start {

    @Test
    public void test() {
        String of = API.Match(5).of(
                // else if (bmi < 18.5)
                Case($(v -> v < 18.5), () -> "有些许晃荡！"),
                // else if (bmi < 25)
                Case($(v -> v < 25), () -> "继续加油哦！"),
                // else if (bmi < 30)
                Case($(v -> v < 30), () -> "你是真的稳！"),
                // else
                Case($(), () -> "难受！")
        );
    }

    @Test
    public void test1() {
        User[] users = Stream.generate(User::supplier).limit(100).toArray(User[]::new);
        Map<Integer, List<User>> tuple2s = List.of(users)
                .groupBy(User::getAge).mapValues(Function.identity());
    }

    @Test
    public void test2(){
        List<Integer> data = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Iterator<List<Integer>> sliding = data.sliding(3, 3);
        for (List<Integer> integers : sliding) {
            log.info("data:{}", integers);
        }
    }
}
