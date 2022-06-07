package org.ilmostro.basic.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.concurrent.semaphore.OnceRunnable;
import org.ilmostro.basic.concurrent.semaphore.WaitOneReturnRunnable;
import org.ilmostro.basic.executor.ThreadPoolExecutorFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class SemaphoreTest {

	Semaphore semaphore;
	ThreadPoolExecutor executor;

	@Before
	public void before() {
		semaphore = new Semaphore(1);
		executor = ThreadPoolExecutorFactory.get(false);
	}


	@Test
	public void test() {
		List<Integer> list = new ArrayList<>();
		Runnable runnable = () -> {
			for (int i = 0; i < 10000; i++) {
				try {
					semaphore.acquire();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				list.add(i);
				semaphore.release();
			}
		};
		List<CompletableFuture<Void>> waits = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			waits.add(CompletableFuture.runAsync(runnable, executor));
		}
		CompletableFuture.allOf(waits.toArray(new CompletableFuture[0])).join();
		log.info("counter: {}", list.size());
	}

	@Test
	public void once() {
		List<CompletableFuture<Void>> waits = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			waits.add(CompletableFuture.runAsync(new OnceRunnable(semaphore), executor));
		}
		CompletableFuture.allOf(waits.toArray(new CompletableFuture[0])).join();
	}

	@Test
	public void waitSomeReturn(){
		List<CompletableFuture<Void>> waits = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			waits.add(CompletableFuture.runAsync(new WaitOneReturnRunnable(semaphore), executor));
		}
		CompletableFuture.allOf(waits.toArray(new CompletableFuture[0])).join();
	}

}
