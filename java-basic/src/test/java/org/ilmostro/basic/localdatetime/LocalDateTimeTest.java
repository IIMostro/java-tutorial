package org.ilmostro.basic.localdatetime;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author li.bowei
 */
@Slf4j
public class LocalDateTimeTest {

    @Test
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        log.info("now :[{}], dayOfMonth:[{}]", now, now.getDayOfYear());
        log.info("now :[{}], dayOfMonth:[{}], ", now, now.getDayOfMonth());
    }
}
