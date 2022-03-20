package org.ilmostro.basic.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Slf4j
public class DeadLock {

    public static void main(String[] args) {
        DeadLock.deadLock();
    }

    /**
     * 死锁
     */
    private static void deadLock(){
        final Object o1 = new Object();
        final Object o2 = new Object();

        Runnable r1 = () ->{
            synchronized (o1){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2){
                    log.info("R1 Done!");
                }
            }
        };

        Runnable r2 = () ->{
            synchronized (o2){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1){
                    log.info("R2 Done!");
                }
            }
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }

}
