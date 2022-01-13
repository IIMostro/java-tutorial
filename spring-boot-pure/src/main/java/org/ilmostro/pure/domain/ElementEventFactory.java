package org.ilmostro.pure.domain;

import com.lmax.disruptor.EventFactory;

/**
 * @author li.bowei
 */
public class ElementEventFactory implements EventFactory<GoodsElement> {

    @Override
    public GoodsElement newInstance() {
        return new GoodsElement();
    }
}
