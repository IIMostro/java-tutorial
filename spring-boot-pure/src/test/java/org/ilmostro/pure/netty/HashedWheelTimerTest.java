package org.ilmostro.pure.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */

@Slf4j
public class HashedWheelTimerTest {

    @Test
    public void test() throws InterruptedException {
        Timer timer = new HashedWheelTimer();
        timer.newTimeout(timeout -> log.info("curr time:{}", System.currentTimeMillis()), 1, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> log.info("curr time:{}", System.currentTimeMillis()), 2, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> log.info("curr time:{}", System.currentTimeMillis()), 3, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> log.info("curr time:{}", System.currentTimeMillis()), 4, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(5);
    }
}
