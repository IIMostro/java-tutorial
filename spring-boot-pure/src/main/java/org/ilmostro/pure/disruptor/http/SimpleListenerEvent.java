package org.ilmostro.pure.disruptor.http;

import java.util.concurrent.atomic.AtomicBoolean;

import com.lmax.disruptor.EventFactory;
import lombok.Getter;
import lombok.ToString;

/**
 * @author li.bowei
 */
@Getter
@ToString
public class SimpleListenerEvent implements EventFactory<SimpleListenerEvent> {

	private String request;
	private String response;
	private final AtomicBoolean isDone = new AtomicBoolean(false);

	public void setRequest(String request) {
		this.request = request;
	}

	public void setResponse(String response) {
		isDone.compareAndSet(true, true);
		this.response = response;
	}

	@Override
	public SimpleListenerEvent newInstance() {
		return new SimpleListenerEvent();
	}
}
