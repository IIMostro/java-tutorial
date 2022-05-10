package org.ilmostro.pure.controller;

import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import com.lmax.disruptor.dsl.Disruptor;
import io.netty.util.concurrent.Promise;
import org.ilmostro.pure.disruptor.http.NettyPromiseEvent;
import org.ilmostro.pure.disruptor.http.SimpleResponseEvent;
import org.ilmostro.pure.disruptor.http.SimpleResponseEventTranslator;
import org.ilmostro.pure.disruptor.http.VavrPromiseEvent;
import org.ilmostro.pure.utils.NettyPromiseFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@RequestMapping("/disruptor")
public class DisruptorController {

	private final Disruptor<SimpleResponseEvent> disruptor;
	private final Disruptor<NettyPromiseEvent> promise;
	private final Disruptor<VavrPromiseEvent> vavr;

	public DisruptorController(
			Disruptor<SimpleResponseEvent> disruptor,
			Disruptor<NettyPromiseEvent> promise,
			Disruptor<VavrPromiseEvent> vavr) {
		this.disruptor = disruptor;
		this.promise = promise;
		this.vavr = vavr;
	}

	@GetMapping("/simple/now")
	public void now(HttpServletResponse response){
		/*
			这种方法不行，因为HttpServletResponse是通过ThreadLocal来存储的，在这里就是每次都是同一个
			HttpServletResponse, 在向前端写数据的时候就会有问题
		*/
		disruptor.publishEvent(new SimpleResponseEventTranslator(response));
	}

	@GetMapping("/promise/netty")
	public String promise() throws Exception{
		final Promise<Long> promise = NettyPromiseFactory.make();
		this.promise.publishEvent(((event, sequence) -> event.setPromise(promise)));
		return String.valueOf(promise.get());
	}

	@GetMapping("/promise/vavr")
	public CompletableFuture<Long> vavr(){
		final io.vavr.concurrent.Promise<Long> promise = io.vavr.concurrent.Promise.make();
		vavr.publishEvent(((event, sequence) -> event.setPromise(promise)));
		return promise.future().toCompletableFuture();
	}
}
