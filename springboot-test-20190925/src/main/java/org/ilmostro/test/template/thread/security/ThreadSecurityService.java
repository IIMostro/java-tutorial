package org.ilmostro.test.template.thread.security;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author li.bowei on 2019-10-11.
 * @description
 */
@Slf4j
public class ThreadSecurityService {

    /**
     * 全局变量，对于全局变量的操作，在web应用中应该使用线程安全的操作。tomcat中每个session都会起一个线程。
     */
    private Integer index = 0;

    private AtomicInteger index1 = new AtomicInteger(0);

    /**
     * 在spring应用中此变量可以理解为注入的变量
     */
    private List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

    /**
     * 错误示例,使用全局变量操作
     */
    public void errorPrint() {
        while (index < list.size()) {
            log.info("thread name is {}, data is {}", Thread.currentThread().getName(), list.get(index));
            index++;
        }
    }

    public void print(){
//        for(Integer data: list){
//            log.info("thread name is {}, data is {}", Thread.currentThread().getName(), data);
//        }
    }

    /**
     * 正确的示例
     */
    public void correctPrint() {
        Integer index = InheritableThreadLocalUtils.get();
        while (index < list.size()) {
            log.info("thread name is {}, data is {}", Thread.currentThread().getName(), list.get(index));
            InheritableThreadLocalUtils.set(index++);
        }
    }

    /**
     * 原子性，此成员变量被多线程访问时也能保证每次只加1.
     */
    public void atomicInteger() {
        int index = index1.getAndIncrement();
        log.info("thread name is {}, index1 is {}", Thread.currentThread().getName(), index);
    }

    public synchronized void correctPrint1(){
        log.info("thread name is {}, data is {}", Thread.currentThread().getName(), list.get(index));
        index++;
    }

    public Integer getData(){
        Integer integer = list.get(index1.getAndIncrement());
        log.info("get data thread name is {}， and data is {}", Thread.currentThread().getName(), integer);
        return integer;
    }
}
