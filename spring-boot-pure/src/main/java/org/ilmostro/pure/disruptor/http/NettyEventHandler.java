package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class NettyEventHandler implements EventHandler<NettyPromiseEvent> {

	private final int mask;
	private final int ordinal;

	public NettyEventHandler(int mask, int ordinal) {
		this.mask = mask;
		this.ordinal = ordinal;
	}

	@Override
	public void onEvent(NettyPromiseEvent event, long sequence, boolean endOfBatch) {
		if ((sequence % mask) != ordinal) return;
		log.info("process goods element:{}, sequence:{}", event, sequence);
		event.getPromise().trySuccess(System.currentTimeMillis());
	}
}
