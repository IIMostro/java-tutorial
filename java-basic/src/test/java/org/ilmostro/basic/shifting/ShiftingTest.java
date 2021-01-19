package org.ilmostro.basic.shifting;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ShiftingTest {

    private static final int NUMBER = 10;

    @Test
    public void left(){
        for(int i = 1; i < 10; i++){
            int number = NUMBER << i;
            log.info("10 << 1 :{}, binary:{}", number, Integer.toBinaryString(number));
        }
    }

    @Test
    public void right(){
        for(int i = 1; i < 10; i++){
            int number = NUMBER >> i;
            log.info("10 >> 1 :{}, binary:{}", number, Integer.toBinaryString(number));
        }
    }

    @Test
    public void rightPlus(){
        for(int i = 1; i < 10; i++){
            int number = NUMBER >>> i;
            log.info("10 >>> 1 :{}, binary:{}", number, Integer.toBinaryString(number));
        }
    }
}
