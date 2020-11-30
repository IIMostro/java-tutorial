package org.ilmostro.basic.completable;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
}
