package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventFactory;
import io.vavr.concurrent.Promise;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author li.bowei
 */
@Getter
@Setter
@ToString
public class VavrPromiseEvent implements EventFactory<VavrPromiseEvent> {

	private Promise<Long> promise;
	private Long id;

	@Override
	public VavrPromiseEvent newInstance() {
		return new VavrPromiseEvent();
	}
}
