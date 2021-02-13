package org.ilmostro.disruptor.mulit;

import com.lmax.disruptor.WorkHandler;
import org.ilmostro.disruptor.entity.GoodsElement;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author IlMostro
 * @date 2021/2/13 下午9:33
 */
public class Consumer implements WorkHandler<GoodsElement> {

    private String consumerId;

    private static AtomicInteger count = new AtomicInteger(0);

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    private Random random = new Random();

    @Override
    public void onEvent(GoodsElement event) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        System.err.println("当前线程: "+ Thread.currentThread().getName() +" 当前消费者: " + this.consumerId + ", 消费信息ID: " + event.getId());
        count.incrementAndGet();
    }

    public Integer getCount(){
        return count.get();
    }
}
