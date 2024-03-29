package org.ilmostro.pure.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import org.ilmostro.pure.domain.GoodsElement;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

/**
 * @author IlMostro
 * @date 2021/2/13 下午9:37
 */
public class Start {

    public static void main(String[] args) throws InterruptedException {
        //1 创建RingBuffer
        RingBuffer<GoodsElement> ringBuffer =
                RingBuffer.create(ProducerType.MULTI,
                        GoodsElement::new,
                        1024 * 1024,
                        new YieldingWaitStrategy());

        //2 通过ringBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //3 创建含有10个消费者的数组:
        Consumer[] consumers = new Consumer[10];
        for(int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        //4 构建多消费者工作池
        WorkerPool<GoodsElement> workerPool = new WorkerPool<>(
                ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers);

        //5 设置多个消费者的sequence序号 用于单独统计消费进度, 并且设置到ringbuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //6 启动workerPool, 这里设置的线程数如果比consumers小则只有线程数多的消费者
        workerPool.start(Executors.newFixedThreadPool(10));

//      final CountDownLatch latch = new CountDownLatch(1);
        CyclicBarrier barrier = new CyclicBarrier(100);

        for(int i = 0; i < 100; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(() -> {
                try {
//                      latch.await();
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(int j = 0; j < 100; j++) {
                    producer.sendData(j);
                }
            }).start();
        }

        System.err.println("----------线程创建完毕，开始生产数据----------");
//      latch.countDown();
        Thread.sleep(10000);
//        System.err.println("任务总数:" + consumers[2].getCount());
    }

    static class EventExceptionHandler implements ExceptionHandler<GoodsElement> {

        public void handleEventException(Throwable ex, long sequence, GoodsElement event) {
        }

        public void handleOnStartException(Throwable ex) {
        }

        public void handleOnShutdownException(Throwable ex) {
        }

    }
}
