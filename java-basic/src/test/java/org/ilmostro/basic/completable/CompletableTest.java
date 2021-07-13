package org.ilmostro.basic.completable;

import ch.qos.logback.core.util.TimeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author li.bowei
 * @date 2020/7/23 16:54
 */
@Slf4j
public class CompletableTest {

    private final Executor executor = ThreadPoolExecutorFactory.get();

    @Test
    public void run() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> log.info(Thread.currentThread().getName()), executor);
        CompletableFuture.allOf(future).join();
    }

    @Test
    public void supply() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(User::supplier, executor)
                .thenApply(User::getPassword)
                .thenAccept(log::info)
                .handle((v1, v2) -> {
                    log.info("this is handler function, current thread name:{}", Thread.currentThread().getName());
                    return v1;
                })
                .exceptionally(v1 -> {
                    log.error("exception cause:{}", v1.getMessage());
                    return null;
                });
        CompletableFuture.allOf(future).join();
    }

    @Test
    public void combine() {
        CompletableFuture<User> user1 = CompletableFuture.supplyAsync(User::supplier, executor);
        CompletableFuture<User> user2 = CompletableFuture.supplyAsync(User::supplier, executor);
        CompletableFuture<Void> future = user1.thenCombine(user2, (user, user21) -> user.getAge() + user21.getAge())
                .thenAccept(v1 -> log.info(v1.toString()));
        CompletableFuture.allOf(future).join();
    }

    @Test
    public void compose() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(User::supplier, executor)
                .thenCompose(user -> CompletableFuture.supplyAsync(user::getName)).thenAccept(log::info);
        CompletableFuture.allOf(future).join();
    }


    @Test
    public void destroy() throws InterruptedException {
        DaemonThread one = new DaemonThread(() -> CompletableFuture.runAsync(new OnceThread("one"), executor).join());
        DaemonThread two = new DaemonThread(() -> CompletableFuture.runAsync(new OnceThread("two"), executor).join());
        one.start();
        two.start();
        TimeUnit.SECONDS.sleep(10);
        one.setInterrupted(true);
        log.info("thread is interrupt :{}", one.isAlive());
        log.info("thread is interrupt :{}", one.isInterrupted());
        TimeUnit.SECONDS.sleep(10);
    }

    private static class DaemonThread extends Thread{

        public DaemonThread(Runnable target) {
            super(target);
        }

        private volatile boolean interrupted = false;

        @Override
        public void run() {
            while(!interrupted){
                super.run();
            }
        }

        public void setInterrupted(boolean interrupted) {
            this.interrupted = interrupted;
        }
    }

    private static class OnceThread implements Runnable{

        private final String name;
        private static final AtomicInteger atomic = new AtomicInteger();

        private OnceThread(String name) {
            this.name = name;
        }

        @SneakyThrows
        @Override
        public void run() {
            log.info("{} :{}", name, atomic.getAndIncrement());
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
