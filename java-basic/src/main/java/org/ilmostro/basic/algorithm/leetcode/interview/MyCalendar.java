package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.TreeMap;

/**
 * @author li.bowei
 */
public class MyCalendar {

    public static void main(String[] args) {
        MyCalendar calendar = new MyCalendar();
        calendar.book(10, 20);
        calendar.book(15, 25);
        calendar.book(20, 30);
    }

    TreeMap<Integer, Integer> calendar;

    public MyCalendar() {
        calendar = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Integer pre = calendar.floorKey(start);
        Integer next = calendar.ceilingKey(start);
        if((pre == null || calendar.get(pre) <=start) &&(next == null || end <= next)){
            calendar.put(start, end);
            return true;
        }
        return false;
    }
}
