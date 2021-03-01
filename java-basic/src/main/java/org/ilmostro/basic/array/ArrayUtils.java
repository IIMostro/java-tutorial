package org.ilmostro.basic.array;

import lombok.extern.slf4j.Slf4j;

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
    }

    public static String[] addFirst(String[] origin, String data) {
        String[] strings = new String[origin.length + 1];
        strings[0] = data;
        System.arraycopy(origin, 0, strings, 1, origin.length);
        return  strings;
    }

}
