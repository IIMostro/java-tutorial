package org.ilmostro.test.template.thread.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author li.bowei on 2019-10-11.
 * @description
 */
@Slf4j
public class InheritableThreadLocalTest {

    private ThreadSecurityService service;

    private ThreadSecurityService service1;

    private LongAdder index;

    private ExecutorService executorService;

    /**
     * 使用@Before，每一个服务都是单例，模拟Spring的@Autowire
     */
    @Before
    public void before(){
        this.service = new ThreadSecurityService();
        this.service1 = new ThreadSecurityService();
        this.index = new LongAdder();
        this.executorService = Executors.newFixedThreadPool(5);
    }

    /**
     * 主线程休眠3s
     * @throws InterruptedException 线程休眠失败
     */
    @After
    public void after() throws InterruptedException {
        Thread.sleep(3000L);
    }

    @Test
    public void errorTest() {
        for (int var1 = 0; var1 < 5; var1++) {
            executorService.execute(service::errorPrint);
        }
    }

    @Test
    public void correctTest() {
        for (int var1 = 0; var1 < 5; var1++) {
            InheritableThreadLocalUtils.set(0);
            executorService.execute(service::correctPrint);
        }
    }

    @Test
    public void correctTest1() {
        for(int i = 0; i< 5; i++){
            executorService.execute(service::atomicInteger);
        }
    }

    @Test
    public void correctTest2() {
        for(int i = 0; i< 5; i++){
            executorService.execute(this::add);
        }
        log.info("Thread name is {}, and index is {}", Thread.currentThread().getName(), index);
    }

    @Test
    public void correctTest3(){
        for(int i = 0; i< 5; i++){
            executorService.execute(() -> {
                service.correctPrint1();
                service1.correctPrint1();
            });
        }
    }

//    @Test
//    public void correctTest4(){
//        for(int i = 0; i< service.size; i++){
//            executorService.execute(service::getData);
//        }
//    }


    private void add(){
        index.increment();
    }

    @Test
    public void test2(){
        for(int i = 0; i< 5; i++){
            executorService.execute(() -> service.print());
        }
    }
}
