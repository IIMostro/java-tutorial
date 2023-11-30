package org.ilmostro.basic.datastruct.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author li.bowei
 */
@Slf4j
public class ArrayTests {

    @Test
    public void test() {
        int[] status = new int[]{1, 2, 3, 4};
        final int[] newStatus = Arrays.copyOf(status, status.length + 1);
        newStatus[newStatus.length - 1] = 5;
        for (int i : newStatus) {
            log.info("i:[{}]", i);
        }
    }

    @Test
    void test_array_binary_search_should_work() throws Exception {
        final var arrays = new String[]{"a", "b", "c"};
        log.info("array binary search 'a' index is:[{}]", Arrays.binarySearch(arrays, "a", String::compareToIgnoreCase));
        log.info("array binary search 'A' index is:[{}]", Arrays.binarySearch(arrays, "A", String::compareToIgnoreCase));
        log.info("array binary search 'd' index is:[{}]", Arrays.binarySearch(arrays, "d", String::compareToIgnoreCase));
        log.info("array binary search 'D' index is:[{}]", Arrays.binarySearch(arrays, "D", String::compareToIgnoreCase));
    }
}
