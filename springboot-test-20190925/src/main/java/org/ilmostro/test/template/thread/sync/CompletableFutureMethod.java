package org.ilmostro.test.template.thread.sync;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.service.autowired.SimulationRemoteService;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author li.bowei on 2019-10-12.
 * @description <ul>
 * <ui>使用CompletableFuture时建议使用自定义线程池</ui>
 * </ul>
 */
@Slf4j
public class CompletableFutureMethod {

    //线程池
    private Executor executor;
    //模拟业务
    private SimulationRemoteService service;

    public CompletableFutureMethod(Executor executor,
                                   SimulationRemoteService serivce) {
        this.executor = executor;
        this.service = serivce;
    }

    public void simple() {
        // runAsync没有返回值
        CompletableFuture<Void> exceptionally = CompletableFuture.runAsync(() -> service.noParamNoResult(), executor)
                // thenRun 不需要传递参数，也没有返回值的执行
                .thenRun(() -> log.info("this task apply success"))
                // exceptionally为异常处理
                .exceptionally(var1 -> {
                    log.info("this method apply error");
                    return null;
                });


        // supplyAsync有返回值，
        CompletableFuture<Void> handle = CompletableFuture.supplyAsync(() -> service.noParamHasResult(), executor)
                // thenApply为处理完后在进行处理传递一个Function，
                .thenApply(String::hashCode)
                // thenAccept为处理完后的消费逻辑传递一个Consumer
                .thenAccept(var1 -> log.info(var1.toString()))
                // handle为任务处理完成后的操作，可用于异常处理，第一个
                .handle((param, throwable) -> {
                    if (Objects.nonNull(throwable)) {
                        log.info("this task apply error, param is {}, error message is {}", param, throwable.getMessage());
                        return null;
                    } else {
                        return param;
                    }
                });

        //使用此方法来阻塞所有线程，等待所有线程执行完毕
        CompletableFuture.allOf(exceptionally, handle).join();
    }
}
