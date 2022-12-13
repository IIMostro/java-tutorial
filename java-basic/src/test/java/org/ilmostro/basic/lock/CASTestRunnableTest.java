package org.ilmostro.basic.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author li.bowei
 */
@Slf4j
public class CASTestRunnableTest {

    @Test
    public void run() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 10000; i++){
            executor.submit(new CASTestRunnable());
        }
        TimeUnit.SECONDS.sleep(10);
        log.info("cas test i:{}", CASTestRunnable.getI());
    }
}
