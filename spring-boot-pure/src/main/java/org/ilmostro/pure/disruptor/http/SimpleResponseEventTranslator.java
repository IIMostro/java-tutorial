package org.ilmostro.pure.disruptor.http;

import javax.servlet.http.HttpServletResponse;

import com.lmax.disruptor.EventTranslator;

/**
 * @author li.bowei
 */
public class SimpleResponseEventTranslator implements EventTranslator<SimpleResponseEvent> {

	private final HttpServletResponse response;

	public SimpleResponseEventTranslator(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void translateTo(SimpleResponseEvent event, long sequence) {
		event.setResponse(response);
	}
}
