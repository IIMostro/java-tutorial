package org.ilmostro.basic.executor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * @author li.bowei
 * @date 2020/7/23 16:20
 */
public class ForkJoinFactory extends ForkJoinWorkerThread implements ForkJoinPool.ForkJoinWorkerThreadFactory {
    /**
     * Creates a ForkJoinWorkerThread operating in the given pool.
     *
     * @param pool the pool this thread works in
     * @throws NullPointerException if pool is null
     */
    protected ForkJoinFactory(ForkJoinPool pool) {
        super(pool);
    }

    public ForkJoinFactory(){
        super(new ForkJoinPool());
    }

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new ForkJoinFactory(pool);
    }

    public static ForkJoinPool getForkJoinPool(int parallelism){
        return new ForkJoinPool(parallelism, new ForkJoinFactory(), null, false);
    }
}
