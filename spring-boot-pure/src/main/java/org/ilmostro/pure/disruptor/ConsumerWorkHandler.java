package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.ilmostro.pure.domain.GoodsElement;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Slf4j
public class ConsumerWorkHandler implements WorkHandler<GoodsElement> {

    private final String consumerId;

    public ConsumerWorkHandler(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(GoodsElement event) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(300, 500));
        log.info("当前线程: "+ Thread.currentThread().getName() +" 当前消费者: " + this.consumerId + ", 消费信息ID: " + event.getId());
    }
}
