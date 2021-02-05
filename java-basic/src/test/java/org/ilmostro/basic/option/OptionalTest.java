package org.ilmostro.basic.option;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;

/**
 * @author li.bowei
 * @date 2020/7/23 17:29
 */
@Slf4j
public class OptionalTest {

    @Test
    public void test(){
        Random random = new Random();
        for(int index = 0; index < 100; index++){
            log.info("random: {}", random.nextInt(10));
        }
    }
}
