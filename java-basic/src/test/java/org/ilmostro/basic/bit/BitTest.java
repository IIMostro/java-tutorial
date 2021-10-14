package org.ilmostro.basic.bit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li.bowei
 */
public class BitTest {

    private static final Logger logger = LoggerFactory.getLogger(BitTest.class);

    @org.junit.Test
    public void test() {
        int symbol = 0;
        logger.info("0|2:{}", symbol | 2);
        logger.info("0&2:{}", symbol & 2);
        logger.info("0^2:{}", symbol ^ 2);
        logger.info("~2:{}", ~2);
    }
}
