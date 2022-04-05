package org.ilmostro.pure.disruptor.order;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.domain.GoodsElement;

/**
 * @author li.bowei
 */
@Slf4j
public class OrderExceptionEventHandler implements EventHandler<GoodsElement> {

    @Override
    public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {
        log.info("encounter error: good:[{}]", event);
    }
}
