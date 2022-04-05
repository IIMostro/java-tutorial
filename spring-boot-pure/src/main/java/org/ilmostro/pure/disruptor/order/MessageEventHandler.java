package org.ilmostro.pure.disruptor.order;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.domain.GoodsElement;

/**
 * @author li.bowei
 */
@Slf4j
public class MessageEventHandler implements EventHandler<GoodsElement>, ExceptionHandler<GoodsElement> {

    @Override
    public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {
//        if (event.getId() % 3 == 0) throw new RuntimeException("自定义错误");
        log.info("message event handler -> good[{}]", event);
    }

    @Override
    public void handleEventException(Throwable ex, long sequence, GoodsElement event) {
        log.error("message handler error, event[{}]", event);
    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
