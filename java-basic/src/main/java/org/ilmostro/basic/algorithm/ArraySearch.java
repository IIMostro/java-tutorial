package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 */
@Slf4j
public class ArraySearch {

    private static final AtomicInteger atomic = new AtomicInteger(0);

    public static void main(String[] args) {
        int[] ints = ArrayTestHelper.create(10000, 0, 10000);
        ArraySort.mergeSort(ints);
        int target = 45;
        int[] i = doBinarySearchFloorAndCeil(ints, target, 0, ints.length);
        List<Integer> collect = Arrays.stream(ints).boxed().collect(Collectors.toList());
        int indexOf = collect.indexOf(target);
        log.info("search index:{}, list index:{}", i, indexOf);
        log.info("search floor:{}, value:{}, ceil:{}, value:{}", i[0], ints[i[0]], i[1], ints[i[1]]);
    }

    public static int binarySearch(int[] arr, int target) {
        assert ArraySort.isSort(arr);
        return doBinarySearch(arr, target, 0, arr.length);
    }

    /**
     * 二分查找法
     *
     * @param arr    需要查找的数组
     * @param target 查找目标值
     * @param l      左边界
     * @param r      右边界
     * @return 查找的的index值
     */
    public static int doBinarySearch(int[] arr, int target, int l, int r) {
        while (l <= r) {
            int mid = l + (r - l) / 2;
            log.info("search time:{}, mid={}, l={}, r={}", atomic.incrementAndGet(), mid, l, r);
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return -1;
    }


    public static int[] doBinarySearchFloorAndCeil(int[] arr, int target, int l, int r) {
        int[] result = new int[2];
        int approach = 0;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            approach =mid;
            log.info("search time:{}, mid={}, l={}, r={}", atomic.incrementAndGet(), mid, l, r);
            if (arr[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        int floor = approach + 1;
        while(arr[floor] == arr[approach]){
            floor--;
        }

        int ceil = approach;
        while(arr[ceil] == arr[approach]){
            ceil++;
        }

        result[0] = floor;
        result[1] = ceil;
        return result;
    }

    /**
     * 使用递归的方法实现二分查找
     *
     * @param arr    需要查找的数组
     * @param target 查找的目标值
     * @param l      左边界
     * @param r      右边界
     * @return 查找到的数组下标
     */
    public static int doBinarySearchRecursion(int[] arr, int target, int l, int r) {
        int mid = l + (r - l) / 2;
        log.info("search time:{}, mid={}, l={}, r={}", atomic.incrementAndGet(), mid, l, r);
        if (arr[mid] == target) {
            return mid;
        }
        if (arr[mid] > target && r > l) {
            return doBinarySearchRecursion(arr, target, l, mid - 1);
        }
        if (arr[mid] < target && r > l) {
            return doBinarySearchRecursion(arr, target, mid + 1, r);
        }
        return -1;
    }
}
