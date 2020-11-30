package org.ilmostro.test.template.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author IlMostro
 * @date 2019-10-20 17:37
 */
@Slf4j
public class ThreadTest {

    /**
     * 模拟5000个请求
     */
    private static final int CLIENT_TOTAL = 5000;
    /**
     * 同时执行的并发数
     */
    private static final int THREAD_TOTAL = 200;

    private Map<Integer, Integer> map = new HashMap<>();

    public void test() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
        final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);

        for(int i = 0; i<CLIENT_TOTAL;i++){
            int finalI = i;
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    map.put(finalI,2);
                } catch (InterruptedException e) {
                    log.info("exception is {}", e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executor.shutdown();
        log.info("size is {}", map.size());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        threadTest.test();
    }
}
