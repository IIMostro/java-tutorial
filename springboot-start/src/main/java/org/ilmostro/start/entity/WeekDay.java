package org.ilmostro.start.entity;

import lombok.Data;

import java.util.List;

@Data
public class WeekDay {

    private String sex;
    private String weekDay;
    private List<WeekTime> weekTimes;
}
