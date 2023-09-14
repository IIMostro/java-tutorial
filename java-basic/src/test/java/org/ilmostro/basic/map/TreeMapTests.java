package org.ilmostro.basic.map;

import org.junit.jupiter.api.Test;

import java.util.TreeMap;

public class TreeMapTests {

    @Test
    void test_ceil() {
        final var map = new TreeMap<>();
        map.put(1, "1");
        map.put(3, "3");
        map.put(5, "5");
        map.put(10, "10");
        final var o = map.ceilingKey(2);
        System.out.println("o = " + o);
        System.out.println(map.floorKey(2));
    }
}
