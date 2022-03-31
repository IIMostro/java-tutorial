package org.ilmostro.pure.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import org.ilmostro.pure.configuration.DisruptorConfiguration;
import org.ilmostro.pure.disruptor.order.*;
import org.ilmostro.pure.domain.GoodsElement;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
public class DisruptorRunnerTest {

    @Test
    public void rhombus() throws InterruptedException {
        Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();

        disruptor.handleEventsWith(new ConsumerEventHandler("consumer-handler-1"));

        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWith(new ConsumerEventHandler("consumer-handler-2"));
        EventHandlerGroup<GoodsElement> and = disruptor.handleEventsWith(new ConsumerEventHandler("consumer-and-1"));
        group.and(and);
        group.then(new ConsumerEventHandler("consumer-then-1"));

        disruptor.start();
        disruptor.publishEvent(((event, sequence) -> event.setId(1)));
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     *  单个disruptor给所有的C-1, C-2, C-3, E-1发送event1, event2, event3
     *  等消费完了之后再给D-1消费
     *<h1>
     *                  e1,e2,e3
     *              ---------------- C1
     *                  e1,e2,e3                e1, e2, e3
     *  disruptor   ---------------- C2      ------------  D1
     *                  e1,e2,e3
     *              ---------------- C3
     *                  e1,e2,e3
     *              ---------------- E3
     *</h1>
     *  其实就是还是要看Event, 等e1被C1,C2,C3,C4都消费了之后再到了D1
     *
     * @throws Exception 错误
     */
    @Test
    public void disruptor1() throws Exception{
        Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();
        EventHandler<GoodsElement>[] handlers = new ConsumerEventHandler[3];
        for (int i = 0; i < 3; i++) {
            handlers[i] = new ConsumerEventHandler("C-" + i);
        }
        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWith(handlers);
        // 当使用到group后的handlerEventsWith后当前面所有的都执行完了才会来到这里
        group.handleEventsWith(new ConsumerEventHandler("D-1"));
        // 如果直接使用的disruptor.handlerEventsWiths则和第一级的同级消费
        disruptor.handleEventsWith(new ConsumerEventHandler("E-1"));
        disruptor.start();
        for (int i = 0; i < 3; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * W和C在同一批次执行，并且无序
     * W,C执行完成之后再S执行
     *
     * @throws Exception 错误
     */
    @Test
    @Deprecated
    public void disruptor2() throws Exception{
        Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();

        WorkHandler<GoodsElement>[] handlers = new ConsumerWorkHandler[3];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new ConsumerWorkHandler("W-" + i);
        }
        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWithWorkerPool(handlers);
        WorkHandler<GoodsElement>[] second = new ConsumerWorkHandler[3];
        for (int i = 0; i < handlers.length; i++) {
            second[i] = new ConsumerWorkHandler("S-" + i);
        }
        group.handleEventsWithWorkerPool(second);
        group.handleEventsWith(new ConsumerEventHandler("C-1"));
        disruptor.start();
        for (int i = 0; i < 3; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * W,S分组同时消费一个消息
     * 然后是HW消费，消费完了之后再是TW消费
     *
     * @throws Exception 错误
     */
    @Test
    @Deprecated
    public void disruptor3() throws Exception{
        Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();

        WorkHandler<GoodsElement>[] handlers = new ConsumerWorkHandler[3];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new ConsumerWorkHandler("W-" + i);
        }

        EventHandlerGroup<GoodsElement> group = disruptor.handleEventsWithWorkerPool(handlers);

        WorkHandler<GoodsElement>[] second = new ConsumerWorkHandler[3];
        for (int i = 0; i < handlers.length; i++) {
            second[i] = new ConsumerWorkHandler("S-" + i);
        }
        EventHandlerGroup<GoodsElement> group2 = disruptor.handleEventsWithWorkerPool(second);
        group.and(group2);
        group.handleEventsWithWorkerPool(new ConsumerWorkHandler("HW-1"));
        group.thenHandleEventsWithWorkerPool(new ConsumerWorkHandler("TW-1"));
        disruptor.start();
        for (int i = 0; i < 3; i++) {
            final int fi = i;
            disruptor.publishEvent(((event, sequence) -> event.setId(fi)));
        }
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void disruptor4(){

    }

    public static WorkHandler<GoodsElement>[] getWorkHandlerPool(int size, Class<? extends WorkHandler<GoodsElement>> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        WorkHandler<GoodsElement>[] ans = new WorkHandler[size];
        Constructor<?>[] constructors = clazz.getConstructors();
        for (int i = 0; i < size; i++) {
            ans[i] = (WorkHandler<GoodsElement>) constructors[0].newInstance();
        }
        return ans;
    }

    /**
     * 这是一个完整的测试流程
     *
     * 首先会去处理订单的逻辑，订单处理完成后处理会员的逻辑， 处理完会员的逻辑之后处理红包和积分，红包和积分处理完成后处理邮件的内容
     *
     * @throws Exception 错误
     */
    @Test
    @Deprecated
    public void full() throws Exception{
        Disruptor<GoodsElement> disruptor = DisruptorConfiguration.getInstance();

        EventHandlerGroup<GoodsElement> orderGroup = disruptor.handleEventsWithWorkerPool(getWorkHandlerPool(10, OrderRecordWorkHandler.class));
        EventHandlerGroup<GoodsElement> vipGroup = orderGroup.handleEventsWithWorkerPool(getWorkHandlerPool(2, VipOrderWorkHandler.class));
        EventHandlerGroup<GoodsElement> integralGroup = vipGroup.handleEventsWithWorkerPool(getWorkHandlerPool(2, IntegralWorkHandler.class));
        EventHandlerGroup<GoodsElement> redGroup = vipGroup.handleEventsWithWorkerPool(getWorkHandlerPool(2, RedPackWorkHandler.class));
        integralGroup.and(redGroup).handleEventsWithWorkerPool(getWorkHandlerPool(4, EmailWorkHandler.class));

        disruptor.start();

        for (int i = 0; i < 4; i++) {
            GoodsElement good = new GoodsElement();
            good.setId(i);
            good.setName("商品" + i);
            good.setDescription("这是商品" + i);
            disruptor.publishEvent(new GoodElementTranslator(), JSON.toJSONString(good));
        }

        TimeUnit.SECONDS.sleep(5);
    }
}
