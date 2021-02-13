package org.ilmostro.disruptor.mulit;

import com.lmax.disruptor.RingBuffer;
import org.ilmostro.disruptor.entity.GoodsElement;

import java.util.Random;

/**
 * @author IlMostro
 * @date 2021/2/13 下午9:36
 */
public class Producer {

    private final RingBuffer<GoodsElement> ringBuffer;

    public Producer(RingBuffer<GoodsElement> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(Integer id) {
        long sequence = ringBuffer.next();
        try {
            GoodsElement order = ringBuffer.get(sequence);
            order.setId(new Random().nextInt());
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
