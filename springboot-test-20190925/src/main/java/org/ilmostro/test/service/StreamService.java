package org.ilmostro.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author li.bowei on 2019-09-26.
 * @description
 */
@Service
@Slf4j
public class StreamService {

    private final ForkJoinPool forkJoinPool;

    private BinaryOperator<Long> function = Math::addExact;

    @Autowired
    public StreamService(ForkJoinPool forkJoinPool) {
        this.forkJoinPool = forkJoinPool;
    }

    public void reduce(List<Long> data) {
        Long value = data.stream().reduce(function).orElse(0L);
        log.info("stream reduce value is {}", value);
    }

    public void parallelReduce(List<Long> data) {
        Long value = forkJoinPool.submit(() -> data.stream().parallel().reduce(function).orElse(0L)).join();
        log.info("parallel value is {}", value);
    }

    public void innerForEach(List<Long> data) {
        long sum = 0;
        for (long number : data) {
            sum += number;
        }
        log.info("inner foreach value is {}", sum);
    }

    public void name(List<Long> data){
        forkJoinPool.submit(() -> data.stream().parallel().peek(var1 -> log.info("thread value")).reduce(function).orElse(0L)).join();
    }

    public void name1(List<Long> data){
        data.stream().peek(var1 -> log.info("thread1 value")).reduce(function).orElse(0L);
    }

    public void list(List<Long> var1, List<Long> var2){
        List<Long> collect = forkJoinPool.submit(() ->var1.stream().parallel().flatMap(var3 -> var2.stream().filter(var3::equals)).collect(Collectors.toList())).join();
    }

    public void list1(List<Long> var1, List<Long> var2){
        List<Long> collect = var1.stream().flatMap(var3 -> var2.stream().filter(var3::equals)).collect(Collectors.toList());
    }
}
