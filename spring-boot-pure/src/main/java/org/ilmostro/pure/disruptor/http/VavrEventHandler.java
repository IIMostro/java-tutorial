package org.ilmostro.pure.disruptor.http;

import java.util.concurrent.TimeUnit;

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
		if ((mask & sequence) != ordinal) return;
		log.info("process goods element:{}, sequence:{}", event, sequence);
		event.getPromise().complete(Try.success(null));
	}
}
