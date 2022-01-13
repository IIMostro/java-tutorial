package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.ilmostro.pure.domain.GoodsElement;

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
            order.setId(id);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
