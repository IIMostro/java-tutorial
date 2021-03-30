package org.ilmostro.basic.time;

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

        LocalDateTime second = LocalDateTime.of(2021, 3, 16, 0,0,0);
        long until = first.until(second, ChronoUnit.WEEKS);
        log.info("until:{}", until);
    }
}
