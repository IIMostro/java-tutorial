package org.ilmostro.basic.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author li.bowei
 */
public class CASTestRunnable implements Runnable{

    private static final AtomicBoolean lock = new AtomicBoolean(false);
    private static int i = 0;

    @Override
    public void run() {
        if(lock.compareAndSet(false, true)){
            i++;
            lock.set(false);
        }
    }

    public static int getI() {
        return i;
    }
}
