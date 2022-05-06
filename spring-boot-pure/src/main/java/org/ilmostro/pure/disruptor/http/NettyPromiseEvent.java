package org.ilmostro.pure.disruptor.http;

import com.lmax.disruptor.EventFactory;
import io.netty.util.concurrent.Promise;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author li.bowei
 */
@Getter
@Setter
@ToString
public class NettyPromiseEvent implements EventFactory<NettyPromiseEvent> {

	private Promise<Void> promise;
	private Long id;

	@Override
	public NettyPromiseEvent newInstance() {
		return new NettyPromiseEvent();
	}
}
