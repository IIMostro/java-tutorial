package org.ilmostro.pure.disruptor.order;

import com.lmax.disruptor.EventHandler;
import org.ilmostro.pure.domain.GoodsElement;

/**
 * @author li.bowei
 */
public class OrderRecordEventHandler implements EventHandler<GoodsElement> {
    @Override
    public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {

    }
}
