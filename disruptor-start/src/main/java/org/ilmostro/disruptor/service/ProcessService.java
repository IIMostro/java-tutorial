package org.ilmostro.disruptor.service;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.disruptor.entity.GoodsElement;

/**
 * @author li.bowei
 */
@Slf4j
public class ProcessService implements EventHandler<GoodsElement> {

    @Override
    public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {
        log.info("this is process service! and current thread is:{}, event:{}", Thread.currentThread().getName(), event.getId());
    }
}
