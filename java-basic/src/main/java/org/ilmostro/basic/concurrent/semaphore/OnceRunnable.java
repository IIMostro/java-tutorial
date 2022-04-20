package org.ilmostro.basic.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class OnceRunnable implements Runnable{
	private final Semaphore semaphore;

	public OnceRunnable(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		if (!semaphore.tryAcquire()){
			log.info("other thread is running return!");
			return;
		}
		log.info("thread:{} acquire, running", Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(2);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaphore.release();
	}
}
