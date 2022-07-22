package org.ilmostro.pure.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ilmostro.pure.utils.ThreadPoolExecutorFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

	@PostMapping("/first")
	public String world(){
		return say();
	}

	@PostMapping("/second")
	public CompletableFuture<String> hello(){
		return CompletableFuture.supplyAsync(AsyncController::say, ThreadPoolExecutorFactory.get());
	}

	private static String say(){
		try {
			TimeUnit.SECONDS.sleep(2);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return "aaa";
	}
}
