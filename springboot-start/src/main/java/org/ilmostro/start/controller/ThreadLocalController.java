package org.ilmostro.start.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author li.bowei
 * @date 2020/8/14 11:52
 */
@RestController
@RequestMapping("/local")
@Slf4j
public class ThreadLocalController {

    private static final InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            10, 10000, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    @GetMapping("/simple")
    public void simple() throws InterruptedException {
        threadLocal.set(1);
        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(this::run, executor);
        }
        Thread.sleep(10000);
    }

    public void run() {
        Integer integer = threadLocal.get();
        log.info("inner thead value:{}", integer);
    }

    @GetMapping("/callable")
    public Callable<String> callable(){
        return () -> UUID.randomUUID().toString();
    }
}
