package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.domain.GoodsElement;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author IlMostro
 * @date 2021/2/13 下午9:33
 */
@Slf4j
public class Consumer implements WorkHandler<GoodsElement>, ExceptionHandler<GoodsElement> {

    private final String consumerId;

    private static final AtomicInteger count = new AtomicInteger(0);

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(GoodsElement event) {
        log.info("当前线程:[{}], 当前消费者:[{}], 消费信息ID:[{}]", Thread.currentThread().getName(), this.consumerId, event.getId());
        count.incrementAndGet();
    }

    public Integer getCount(){
        return count.get();
    }

    @Override
    public void handleEventException(Throwable ex, long sequence, GoodsElement event) {
        log.error("消费错误: [{}]", event);
    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
