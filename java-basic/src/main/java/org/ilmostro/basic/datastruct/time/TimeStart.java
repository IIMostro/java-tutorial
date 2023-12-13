package org.ilmostro.basic.datastruct.time;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author li.bowei
 **/
@Slf4j
public class TimeStart {

    public static void main(String[] args) {
//        Duration expired = Duration.ofHours(2);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        LocalDateTime now = LocalDateTime.now();
//        String orderStartTime = now.format(formatter);
//        String orderEndTime = now.plusSeconds(expired.getSeconds()).format(formatter);
//
//        log.info("start time:{}, end time:{}", orderStartTime, orderEndTime);
        int i = 1;
        int j = 2;
        String date = i+String.valueOf(j);
        log.info("data:{}", date);
    }
}
