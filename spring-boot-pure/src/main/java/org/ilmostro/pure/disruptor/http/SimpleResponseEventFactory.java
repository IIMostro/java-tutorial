package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventFactory;

/**
 * @author li.bowei
 */
public class SimpleResponseEventFactory implements EventFactory<SimpleResponseEvent> {

	@Override
	public SimpleResponseEvent newInstance() {
		return new SimpleResponseEvent();
	}
}
