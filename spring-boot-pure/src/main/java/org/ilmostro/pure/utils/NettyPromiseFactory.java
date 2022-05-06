package org.ilmostro.pure.utils;

import java.util.Objects;
import java.util.concurrent.Executor;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class NettyPromiseFactory {

	private static volatile NioEventLoopGroup group;

	public static <T> Promise<T> make(Executor executor){
		if (Objects.isNull(group)){
			synchronized (NettyPromiseFactory.class){
				if (Objects.isNull(group)){
					group = new NioEventLoopGroup(1, executor);
				}
			}
		}
		EventLoop next = group.next();
		return new DefaultPromise<>(next);
	}

	public static <T> Promise<T> make(){
		return make(ThreadPoolExecutorFactory.get());
	}
}
