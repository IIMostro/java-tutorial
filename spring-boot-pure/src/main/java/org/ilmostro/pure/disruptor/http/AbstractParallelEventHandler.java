package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventHandler;

/**
 * @author li.bowei
 */
public abstract class AbstractParallelEventHandler<T> implements EventHandler<T> {

	private final int mask;
	private final int ordinal;

	public AbstractParallelEventHandler(int mask, int ordinal) {
		this.mask = mask;
		this.ordinal = ordinal;
	}

	@Override
	public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
		if ((sequence % mask) != ordinal) return;
		onEvent(event);
	}

	protected abstract void onEvent(T event) throws Exception;
}
