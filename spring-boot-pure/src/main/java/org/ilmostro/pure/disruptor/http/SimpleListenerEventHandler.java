package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventHandler;

/**
 * @author li.bowei
 */
public class SimpleListenerEventHandler implements EventHandler<SimpleListenerEvent> {

	private final int mask;
	private final int ordinal;

	public SimpleListenerEventHandler(int mask, int ordinal) {
		this.mask = mask;
		this.ordinal = ordinal;
	}

	@Override
	public void onEvent(SimpleListenerEvent event, long sequence, boolean endOfBatch) throws Exception {
		if ((mask & sequence) != ordinal) return;
		event.setResponse("success" + sequence);
	}
}
