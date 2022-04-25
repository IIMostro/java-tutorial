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

    @Test
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
        logger.info("status >> 2 [{}] & 1<<2[{}], value:[{}]", Integer.toBinaryString(status >> 2), Integer.toBinaryString(1 << 2),
                (status >> 2) & (1 << 2));
        logger.info("1111 & 1000 : [{}]", Objects.equals(0b1111 & 0b1000, 0b1000));

        int right = 1101 >> 4 & 1;
        logger.info("1101 >> 4 ({}) & 1 [{}]", right, Objects.equals(right, 1));
    }


    @Test
    public void test2() {
        int number = 0b101101001;
        for (int i = 0; i < 9; i++) {
            logger.info("number:[{}] >> [{}] == [{}({})]", Integer.toBinaryString(number), i, Integer.toBinaryString(number >> i), number >> i);
        }

        for (int i = 0; i < 8; i++) {
            logger.info("number:[{}] << [{}] == [{}({})]", Integer.toBinaryString(number), i, Integer.toBinaryString(number << i), number << i);
        }
    }

    @Test
    public void test3() {
        int number = 0b11;
        logger.info("1 << 3 | 0b11 = [{}]", Integer.toBinaryString(1 << 2 | number));
    }

    @Test
    public void test4() {
        int parameter = 0b10101;
        for (int i = 0; i < 5; i++) {
            logger.info("1 << {} & {} = {}", i, Integer.toBinaryString(parameter), Integer.toBinaryString((1 << i) & parameter));
        }
    }

    @Test
    public void test5() {
        int n = 31;
        logger.info("n:[{}], n binary:[{}], n-1 binary:[{}], n & (n-1):[{}]",
                n, Integer.toBinaryString(n), Integer.toBinaryString(n - 1), n & (n - 1));
    }

    @Test
    public void test6() {
        int x = 30;
        int y = 31;
        int z = x ^ y;
        logger.info("x ^ y = {}", z);
        logger.info("z ^ x = {}", z ^ x);
    }

    @Test
    public void test7() {

        int x = 0b1011;
        int y = 0b100;

        logger.info("{} | {} = {}", x, y, Long.toBinaryString(x | y));
        logger.info("{} & {} = {}", x, y, Long.toBinaryString(x & y));
        logger.info("{} ^ {} = {}", x, y, Long.toBinaryString(x ^ y));

        logger.info("{}", Integer.toBinaryString((x | y) ^ y));

        logger.info("{}", Integer.toBinaryString(x ^ 1 << 3));

        logger.info("{}", Long.toBinaryString(Long.MAX_VALUE));
    }

    @Test
    public void swap() {
        int x = 1, y = 2;
        logger.info("x:[{}], y:[{}]", x, y);
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        logger.info("x:[{}], y:[{}]", x, y);
    }


    @Test
    public void test8() {
        long x = 1;
        for (int i = 0; i < 10; i++) {
            x |= 1 << i;
            logger.info("x:[{}], i:[{}]", Long.toBinaryString(x >> 1), i);
        }
    }

    @Test
    public void test9() {
        int a = 3, b = 4;
        logger.info("a:{}, b:{}", a, b);
        a = a ^ b;
        logger.info("a:{}, b:{}", a, b);
        b = a ^ b;
        logger.info("a:{}, b:{}", a, b);
        a = a ^ b;
        logger.info("a:{}, b:{}", a, b);
    }


    @Test
    public void test10(){
        logger.info("1 << 10 :{}", 1024 >> 9);
    }

}
