package org.ilmostro.basic.bit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

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

    @Test
    public void test1() {
        int status = 0b1111;
        logger.info("status >> 2 [{}] & 1<<2[{}], value:[{}]", Integer.toBinaryString(status >> 2), Integer.toBinaryString(1<<2),
                (status >> 2) & (1 <<2));
        logger.info("1111 & 1000 : [{}]", Objects.equals(0b1111 & 0b1000, 0b1000));

        int right = 1101 >> 4 & 1;
        logger.info("1101 >> 4 ({}) & 1 [{}]",right, Objects.equals(right, 1));
    }


    @Test
    public void test2(){
        int number = 0b101101001;
        for (int i = 0; i < 9; i++) {
            logger.info("number:[{}] >> [{}] == [{}({})]", Integer.toBinaryString(number), i, Integer.toBinaryString(number >> i), number >> i);
        }

        for (int i = 0; i < 8; i++) {
            logger.info("number:[{}] << [{}] == [{}({})]", Integer.toBinaryString(number), i, Integer.toBinaryString(number << i), number << i);
        }
    }
}
