package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.domain.GoodsElement;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author li.bowei
 */
@Slf4j
public class ConsumerEventHandler implements EventHandler<GoodsElement>, WorkHandler<GoodsElement> {

    private final String consumerId;

    private static final AtomicInteger count = new AtomicInteger(0);

    public ConsumerEventHandler(String consumerId) {
        this.consumerId = consumerId;
    }

    private final Random random = new Random();

    @Override
    public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {
        Thread.sleep(random.nextInt(5));
        log.info("当前线程: "+ Thread.currentThread().getName() +" 当前消费者: " + this.consumerId + ", 消费信息ID: " + event.getId());
        count.incrementAndGet();
    }

    @Override
    public void onEvent(GoodsElement event) throws Exception {
        Thread.sleep(random.nextInt(5));
        log.info("当前线程: "+ Thread.currentThread().getName() +" 当前消费者: " + this.consumerId + ", 消费信息ID: " + event.getId());
        count.incrementAndGet();
    }
}
