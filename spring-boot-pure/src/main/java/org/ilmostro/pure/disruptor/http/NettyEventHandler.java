package org.ilmostro.pure.disruptor.http;

import java.util.concurrent.TimeUnit;

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
	public void onEvent(NettyPromiseEvent event, long sequence, boolean endOfBatch) throws Exception {
		if ((mask & sequence) != ordinal) return;
		log.info("process goods element:{}, sequence:{}", event, sequence);
		event.getPromise().trySuccess(null);
	}
}
