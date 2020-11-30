package org.ilmostro.test.template.aqs;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author IlMostro
 * @date 2019-10-22 20:50
 */
@Slf4j
public class CountDownLatchTest {

    public static final int THREAD = 100;

    private CountDownLatch latch;

    private ExecutorService executor;

    @Before
    public void before() {
        this.executor = Executors.newCachedThreadPool();
        this.latch = new CountDownLatch(THREAD);
    }

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < THREAD; i++) {
            executor.execute(() -> {
                latch.countDown();
                log.info("latch is{}", latch.getCount());
            });
        }
        latch.await();
        executor.shutdown();
    }

}
