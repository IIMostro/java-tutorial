package org.ilmostro.basic.datastruct.time;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author li.bowei
 **/
@Slf4j
public class PeriodStart {


    public static void main(String[] args) {
        LocalDateTime first = LocalDateTime.now();

        LocalDateTime second = LocalDateTime.of(2021, 5, 16, 0,0,0);
        long until = second.until(first, ChronoUnit.DAYS);
        log.info("until:{}", until);
    }
}
