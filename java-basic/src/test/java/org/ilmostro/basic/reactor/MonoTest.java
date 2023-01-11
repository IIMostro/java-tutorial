package org.ilmostro.basic.reactor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * @author li.bowei
 */
@Slf4j
public class MonoTest {

    private static final Logger logger = Loggers.getLogger(MonoTest.class);

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
                .publishOn(scheduler)
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

    @Test
    public void test1() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Schedulers.fromExecutor(scheduledExecutorService).schedulePeriodically(() -> log.info("current:{}", System.currentTimeMillis()), 1,1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void subscriber() throws Exception{
        Flux.just(new int[]{1,2,3,4})
                .metrics()
                .flatMapIterable((Function<int[], Iterable<?>>) ints -> Arrays.stream(ints).boxed().collect(Collectors.toList()))
                .publishOn(Schedulers.fromExecutor(ThreadPoolExecutorFactory.get(false)))
//                .subscribeOn(Schedulers.fromExecutor(ThreadPoolExecutorFactory.get(false)))
                .subscribeWith(new SampleSubscriber<>());

        Mono.just(Arrays.asList(1, 2, 3))
                .metrics()
                .flatMapIterable(Function.identity())
                .doOnError(v1 -> log.error(v1.getMessage()))
                .publishOn(Schedulers.fromExecutor(ThreadPoolExecutorFactory.get(false)))
                .subscribeWith(new SampleSubscriber<>()).request(10);

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void zip() throws Exception{
        Mono.zip(Mono.just(1), Mono.just(2))
                .subscribeOn(Schedulers.fromExecutor(ThreadPoolExecutorFactory.get(false)))

                .subscribe(v1 -> log.info("v1:{}, v2:{}", v1.getT1(), v1.getT2()));
//        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void checkpoint(){
        Mono.just(2)
                .map(v1 -> v1 + 1)
                .checkpoint("description", true)
                .log(logger)
                .subscribe();
    }


    @Test
    public void useSubscribeOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i + ":" + Thread.currentThread())
                .subscribeOn(s)
                .map(i -> "value " + i + ":"+ Thread.currentThread());

        new Thread(() -> flux.subscribe(System.out::println), "ThreadA").start();
        Thread.sleep(5000);
    }

    @Test
    public void usePublishOn() throws InterruptedException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i + ":"+ Thread.currentThread())
                // publishOn changes the scheduler for the next operator only
                // 会把下一个操作符的执行线程切换到指定的线程池
                .publishOn(s)
                .map(i -> "value " + i+":"+ Thread.currentThread());

        new Thread(() -> flux.subscribe(System.out::println),"ThreadA").start();
        System.out.println(Thread.currentThread());
        Thread.sleep(5000);
    }

    @Test
    public void subscription() throws Exception{
        Flux.just(1,2,3)
                .doOnSubscribe(v1 -> log.info("doOnSubscribe"))
                .doOnNext(v1 -> log.info("doOnNext"))
                .doOnRequest(v1 -> log.info("doOnRequest"))
                .doOnCancel(() -> log.info("doOnCancel"))
                .doOnComplete(() -> log.info("doOnComplete"))
                .doOnTerminate(() -> log.info("doOnTerminate"))
                .doFinally(v1 -> log.info("doFinally"))
                .doOnDiscard(Object.class, v1 -> log.info("doOnDiscard"))
                .doOnEach(v1 -> log.info("doOnEach"))
                .doOnError(v1 -> log.info("doOnError"))
                .doOnTerminate(() -> log.info("doOnTerminate"))
                .subscribeOn(Schedulers.fromExecutor(ThreadPoolExecutorFactory.get(false)))
                .subscribe();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void fold(){
        List<Mono<Integer>> monos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            monos.add(Mono.just(i));
        }
        Mono.zip(monos, objects -> {
            int sum = 0;
            for (Object object : objects) {
                log.info("object:{}", object);
                sum += (int) object;
            }
            return sum;
        }).subscribe(v1 -> log.info("sum:{}", v1));
    }
}
