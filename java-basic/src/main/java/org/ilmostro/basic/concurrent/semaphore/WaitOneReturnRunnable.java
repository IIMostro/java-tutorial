package org.ilmostro.basic.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author li.bowei
 */
@Slf4j
public class WaitOneReturnRunnable implements Runnable{

	private final Semaphore semaphore;
	private static String message;

	public WaitOneReturnRunnable(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@SneakyThrows
	@Override
	public void run() {
		if(StringUtils.isNotBlank(message)) {
			log.info("message not blank message hash code:{}", System.identityHashCode(message));
			return;
		}
		while(true){
			if(StringUtils.isNotBlank(message)) {
				log.info("message in try acquire not blank, hash code:{}", System.identityHashCode(message));
				return;
			}
			if (semaphore.tryAcquire()){
				TimeUnit.SECONDS.sleep(1);
				message = new String("a");
				log.info("message init success! hash code:{}", System.identityHashCode(message));
				semaphore.release();
				return;
			}
		}
	}
}
