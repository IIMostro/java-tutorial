package org.ilmostro.basic.time;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author li.bowei
 **/
@Slf4j
public class UnionIdGenerator {

    static AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) {
        int count  = 10;
        while(count > 0){
            String id = id("1");
            log.info("generator id:{}", id);
            count--;
        }
    }

    public static String id(String symbol){
        StringBuilder sb = new StringBuilder();
        sb.append(symbol);
        int length = StringUtils.length(symbol);
        while (length < 5){
            sb.append(0);
            length++;
        }
        sb.append(System.currentTimeMillis());
        long redisCursor = atomicLong.getAndIncrement();
        int cursorLength = String.valueOf(redisCursor).length();
        sb.append(redisCursor);
        while (cursorLength < 10){
            sb.append(0);
            cursorLength ++;
        }
        return sb.toString();
    }
}
