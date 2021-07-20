package org.ilmostro.basic.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Slf4j
public class MonoTest {

    @Test
    public void mono(){
        Mono.just("hello").subscribe(log::info);
    }

    @Test
    public void monoError(){
        Mono.just("hello")
                .map(var1 -> {
                    if(var1.length() >0){
                        throw new RuntimeException();
                    }
                    return "error";
                })
                .doOnError(var1 -> log.error(var1.getMessage()))
                .subscribe(log::info);
    }

    @Test
    public void delay() throws InterruptedException {
        Mono.just("hello")
                .delaySubscription(Duration.ofSeconds(1))
                .subscribe(log::info);
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void runnable() throws InterruptedException {
        Thread executor = new Thread(() -> Mono.fromRunnable(() -> {
            try {
                log.info("start");
                TimeUnit.SECONDS.sleep(2);
                log.info("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).subscribe(var1 -> log.info("this is subscribe")));
        log.info("executor start");
        executor.start();
        TimeUnit.SECONDS.sleep(1);
        executor.stop();
        log.info("executor stop");
        TimeUnit.SECONDS.sleep(3);
    }
}
