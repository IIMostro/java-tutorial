package org.ilmostro.basic.concurrent.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class ThreadPoolExecutorState {

	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;
	private final AtomicInteger state = new AtomicInteger(-1 << COUNT_BITS);

	private static void print(String prefix, AtomicInteger state){
		log.info("{}, state:{}, bit:{}, length:{}", prefix, state.intValue(),
				Integer.toBinaryString(state.intValue()),
				Integer.toBinaryString(state.intValue()).length());
	}

	public void addWorker(){
		print("before",state);
		for (int i = 0; i < 10; i++) {
			state.compareAndSet(state.intValue(), state.intValue() + 1);
		}
		print("after",state);
	}

	public boolean isRunning(){
		return state.intValue() < 0;
	}

	public void shutdown(){
		print("shutdown before",state);
		state.compareAndSet(((-1 << COUNT_BITS) | workerCount()), ((2 << COUNT_BITS) | workerCount()));
		print("shutdown after",state);
	}

	public int workerCount(){
		return COUNT_MASK & state.intValue();
	}

	public int runStateOf(){
		return state.intValue() & ~COUNT_MASK;
	}

	public static void main(String[] args) {
		ThreadPoolExecutorState state = new ThreadPoolExecutorState();
		log.info("state running state:{}", state.isRunning());
		int i = state.runStateOf();
		log.info("run state of:{}", Integer.toBinaryString(i));
		state.addWorker();
		state.shutdown();
		log.info("state running state:{}", state.isRunning());
	}
}
