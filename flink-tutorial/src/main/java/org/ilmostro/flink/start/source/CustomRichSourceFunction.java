package org.ilmostro.flink.start.source;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.concurrent.TimeUnit;

public class CustomRichSourceFunction extends RichSourceFunction<String> implements ParallelSourceFunction<String> {

    private volatile boolean interrupt = true;

    @Override
    public void open(Configuration parameters) {
        System.err.println("custom rich source function is open thread name:" + Thread.currentThread().getName());
    }

    @Override
    public void close() {
        System.err.println("custom rich source function is close thread name:" + Thread.currentThread().getName());
    }

    @Override
    public void run(SourceContext ctx) throws InterruptedException {
        while (interrupt){
            //noinspection unchecked
            ctx.collect(RandomStringUtils.random(1, "ABCDEFGIJKLMNO"));
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    @Override
    public void cancel() {
        interrupt = false;
    }
}
