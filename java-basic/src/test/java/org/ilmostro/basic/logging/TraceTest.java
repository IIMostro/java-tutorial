package org.ilmostro.basic.logging;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author li.bowei
 */
public class TraceTest {

    private static final Logger logger = LoggerFactory.getLogger(TraceTest.class);

    @Test
    public void test(){
        String curr = UUID.randomUUID().toString();
        MDC.put("TraceId", curr);
        MDC.put("SpanId", curr);
        logger.info("sss");
    }
}
