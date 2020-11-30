package org.ilmostro.test.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei on 2019-09-26.
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StreamTests {

    @Autowired
    private StreamService service;

    private List<Long> var1 = Stream.generate(new Random()::nextInt)
            .limit(100000)
            .map(Integer::longValue)
            .collect(Collectors.toList());

    private List<Long> var2 = Stream.generate(new Random()::nextInt)
            .limit(100000)
            .map(Integer::longValue)
            .collect(Collectors.toList());

    @Test
    public void time() {
        service.reduce(var1);
        service.parallelReduce(var1);
        service.innerForEach(var1);
    }

    @Test
    public void name(){
        service.name1(var1);
    }

    @Test
    public void list(){
        service.list1(var1,var2);
        service.list(var1, var2);
    }
}
