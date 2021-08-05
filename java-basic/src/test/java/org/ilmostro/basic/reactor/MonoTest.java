package org.ilmostro.basic.reactor;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void monoError1(){
        Mono.just("hello")
                .map(var1 -> {
                    if(var1.length() >0){
                        throw new RuntimeException();
                    }
                    return "error";
                })
                .subscribe(log::info, ex -> log.info("error!{}", ex.getMessage()));
    }

    @Test
    public void monoThread() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.get(true);
        Scheduler scheduler = Schedulers.fromExecutor(threadPoolExecutor);
        Flux.range(1,10)
                .subscribeOn(scheduler)
                .map(Math::abs)
                .map(Objects::toString)
                .log()
                .subscribe(log::info, ex -> log.info(ex.getMessage()), () -> log.info("complete"));
        TimeUnit.SECONDS.sleep(1);
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

    @Test
    public void test() {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.get(true);
        Scheduler scheduler = Schedulers.fromExecutor(threadPoolExecutor);
        AtomicInteger atomic = new AtomicInteger();
        Mono.fromRunnable(() ->{
            for (int i = 0; i < 1000000; i++) {
                atomic.getAndIncrement();
            }
        }).subscribeOn(scheduler).subscribe();
        System.out.println(atomic);
    }
}
