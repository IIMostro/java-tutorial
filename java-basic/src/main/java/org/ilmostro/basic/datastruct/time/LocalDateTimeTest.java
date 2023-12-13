package org.ilmostro.basic.datastruct.time;


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author li.bowei
 **/
@Slf4j
public class LocalDateTimeTest {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.of("Asia/Shanghai")).toInstant());
        log.info("date:{}", date);
    }
}
