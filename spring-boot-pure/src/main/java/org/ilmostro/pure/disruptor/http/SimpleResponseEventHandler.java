package org.ilmostro.pure.disruptor.http;

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class SimpleResponseEventHandler extends AbstractParallelEventHandler<SimpleResponseEvent> {

	public SimpleResponseEventHandler(int mask, int ordinal) {
		super(mask, ordinal);
	}

	@Override
	public void onEvent(SimpleResponseEvent event) throws Exception {
		log.info("receive event response:{}", event.getResponse());
		final PrintWriter writer = event.getResponse().getWriter();
		writer.print(System.currentTimeMillis());
		writer.flush();
	}
}
