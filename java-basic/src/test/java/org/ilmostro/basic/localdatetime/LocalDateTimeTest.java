package org.ilmostro.basic.localdatetime;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    @Test
    public void end(){
        final LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        log.info("end:[{}]", DateTimeFormatter.ISO_DATE_TIME.format(end.atZone(ZoneId.systemDefault())));

        final Duration between = Duration.between(LocalDateTime.now(), end);
        log.info("between:[{}]", between.getSeconds());
    }
}
