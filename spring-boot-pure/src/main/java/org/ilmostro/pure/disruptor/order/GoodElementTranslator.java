package org.ilmostro.pure.disruptor.order;

import com.lmax.disruptor.EventTranslatorOneArg;
import org.ilmostro.pure.domain.GoodsElement;

/**
 * @author li.bowei
 */
public class GoodElementTranslator implements EventTranslatorOneArg<GoodsElement, Integer> {
    
    @Override
    public void translateTo(GoodsElement event, long sequence, Integer arg0) {
        event.setId(arg0);
    }
}
