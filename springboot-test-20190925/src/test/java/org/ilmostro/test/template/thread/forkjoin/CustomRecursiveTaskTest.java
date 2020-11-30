package org.ilmostro.test.template.thread.forkjoin;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

/**
 * @author li.bowei on 2019-10-11.
 * @description
 */
@Slf4j
public class CustomRecursiveTaskTest {

    @Test
    public void test(){
        //生成100个随机的int数字
        Integer[] data = Stream.generate(new Random()::nextInt).limit(100).toArray(Integer[]::new);
        //noinspection ConfusingArgumentToVarargsMethod
        log.info("random generate data is {}", data);
        //获取当前可用线程数
        int available = Runtime.getRuntime().availableProcessors();
        log.info("this machine available cpu core count is {}", available);
        //创建forkJoinPoll
        ForkJoinPool forkJoinPool = new ForkJoinPool(available);

        Integer sum = forkJoinPool.invoke(new CustomRecursiveTask(data));
        assertNotNull(sum);
        log.info("this array sum is {}", sum);
    }
}