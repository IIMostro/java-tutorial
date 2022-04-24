package org.ilmostro.basic.threadpoolexecutor;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class ThreadRunningStateTest {

	private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;

	// runState is stored in the high-order bits

	// -1 = 11111111111111111111111111111111 << 29 = 11100000000000000000000000000
	private static final int RUNNING    = -1 << COUNT_BITS;
	private static final int SHUTDOWN   =  0 << COUNT_BITS;
	private static final int STOP       =  1 << COUNT_BITS;
	private static final int TIDYING    =  2 << COUNT_BITS;
	private static final int TERMINATED =  3 << COUNT_BITS;

	// Packing and unpacking ctl
	private static int runStateOf(int c)     { return c & ~COUNT_MASK; }
	private static int workerCountOf(int c)  { return c & COUNT_MASK; }
	private static int ctlOf(int rs, int wc) { return rs | wc; }

	void compareAndIncrementWorkerCount(int expect) {
		ctl.compareAndSet(expect, expect + 1);
	}

	@Test
	public void init(){
		printInteger("COUNT_BITS", COUNT_BITS);
		printInteger("COUNT_MASK", COUNT_MASK);
		printInteger("CTL", ctl.intValue());
		printInteger("RUNNING", RUNNING);
		printInteger("SHUTDOWN", SHUTDOWN);
		printInteger("STOP", STOP);
		printInteger("TIDYING", TIDYING);
		printInteger("TERMINATED", TERMINATED);
	}


	private static void printInteger(String name, Integer value){
		log.info("{} value:{}, bit:{}, length:{}", name, value,
				Integer.toBinaryString(value),
				Integer.toBinaryString(value).length());
	}

	@Test
	public void addWorker(){
		printInteger("CTL", ctl.intValue());
		for (int i = 0; i < 10; i++) {
			compareAndIncrementWorkerCount(ctl.get());
		}
		printInteger("CTL", ctl.intValue());
	}

	@Test
	public void state(){
		printInteger("~COUNT_MASK", ~COUNT_MASK);
		printInteger("RUNNING state", runStateOf(ctlOf(RUNNING, 0)));
		printInteger("SHUTDOWN state", runStateOf(ctlOf(SHUTDOWN, 0)));
	}
}
