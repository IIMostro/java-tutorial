package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@Slf4j
public class ArrayTestHelper {

    /**
     * 创建数组
     *
     * @param n      长度
     * @param rangeL 左边界
     * @param rangeR 右边界
     * @return 创建的数组
     */
    static int[] create(int n, int rangeL, int rangeR) {
        assert n > 0 && rangeL > 0 && rangeR > 0 && rangeR > rangeL;
        Random random = new Random();
        return Stream.generate(() -> random.nextInt(rangeR + rangeL) - rangeL).limit(n).mapToInt(Integer::intValue).toArray();
    }

    static int[] createOrderArray(int n, int rangeL){
        assert n > 0 && rangeL > 0;
        int [] arr = new int[n];
        for(int index = 0; index < n; index ++){
            arr[index] = rangeL++;
        }
        return arr;
    }


    /**
     * 打印数组
     *
     * @param arr 数组
     * @param n   长度
     */
    static void printArray(int[] arr, int n) {
        if (Objects.isNull(arr) || arr.length <= 0) {
            return;
        }
        if (n <= 0 || n > arr.length) {
            return;
        }
        String collect = Arrays.stream(arr).limit(n).boxed().map(Objects::toString).collect(Collectors.joining(","));
        log.info("arr:{}", collect);
    }

    /**
     * 数组交换
     *
     * @param arr 数组
     * @param i   i
     * @param j   j
     */
    static void swap(int[] arr, int i, int j) {
        if (Objects.isNull(arr) || arr.length <= 0) {
            return;
        }
        if (i < 0 || i > arr.length) {
            return;
        }
        if (j < 0 || j > arr.length) {
            return;
        }

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void accept(String name, Consumer<int[]> consumer, int[] arr){
        long start = System.currentTimeMillis();
        consumer.accept(arr);
        if(!isSort(arr)){
            throw new RuntimeException("没有排序成功");
        }
        log.info("function name:{}, expend:{} ms", name, System.currentTimeMillis() - start);
    }

    /**
     * 当前数组是否已经排序成功
     *
     * @param arr 数组
     * @return 是否已经排序成功
     */
    static boolean isSort(int[] arr){
        for(int index = 0; index < arr.length - 1; index++){
            if(arr[index] > arr[index + 1]){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int[] arr = create(10, 0, 1000);
        printArray(arr, 3);
    }
}
