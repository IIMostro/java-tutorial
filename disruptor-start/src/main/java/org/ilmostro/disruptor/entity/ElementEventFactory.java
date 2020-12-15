package org.ilmostro.disruptor.entity;

import com.lmax.disruptor.EventFactory;

/**
 * @author li.bowei
 */
public class ElementEventFactory implements EventFactory<SheetElement> {

    @Override
    public SheetElement newInstance() {
        return new SheetElement();
    }
}
