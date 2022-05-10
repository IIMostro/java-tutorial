package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventHandler;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class VavrEventHandler implements EventHandler<VavrPromiseEvent> {

	private final int mask;
	private final int ordinal;

	public VavrEventHandler(int mask, int ordinal) {
		this.mask = mask;
		this.ordinal = ordinal;
	}

	@Override
	public void onEvent(VavrPromiseEvent event, long sequence, boolean endOfBatch) throws Exception{
		if ((sequence % mask) != ordinal) return;
		log.info("process goods element:{}, sequence:{}", event, sequence);
		event.getPromise().complete(Try.success(System.currentTimeMillis()));
	}
}
