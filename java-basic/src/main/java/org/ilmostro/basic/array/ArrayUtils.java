package org.ilmostro.basic.array;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author li.bowei
 **/
@Slf4j
public class ArrayUtils {

    public static void main(String[] args) {
        String[] strings = new String[]{"a", "b", "c"};
        for (String data : addFirst(strings, "1")) {
            log.info(data);
        }

        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> integers1 = new ArrayList<>(Arrays.asList(4, 5, 6, 7, 8));
        integers1.retainAll(integers);
        integers.removeAll(integers1);
        System.out.println(integers);
    }

    public static String[] addFirst(String[] origin, String data) {
        String[] strings = new String[origin.length + 1];
        strings[0] = data;
        System.arraycopy(origin, 0, strings, 1, origin.length);
        return  strings;
    }

}
