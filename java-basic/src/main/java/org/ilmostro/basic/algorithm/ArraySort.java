package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class ArraySort extends ArrayTestHelper {

    public static void main(String[] args) {
//        int number = 10000000;
//        int[] ints = create(number, 100, number);
//        accept("selection", ArraySort::heapSort, ints);
//        printArray(ints, 10);
        int[] ints = create(1000, 0, 1000);
        accept("merge", ArraySort::mergeSort, ints);
    }

    /**
     * 选择排序, 先找到最小的一个数值插入到第一个，以此类推
     *
     * @param arr 素组
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }

    /**
     * 插入排序
     *
     * @param arr 数组
     */
    public static void insertSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int currentIndex = i;
            for (int j = i + 1; j > 0; j--) {
                if (arr[currentIndex] < arr[j]) {
                    break;
                }
                swap(arr, currentIndex--, j);
            }
        }
    }

    /**
     * 插入排序
     *
     * @param arr 数组
     */
    public static void insertSortV1(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
                swap(arr, j, j - 1);
            }
        }
    }

    /**
     * 插入排序优化，先复制出当前位置的值，然后将前面的数值往前移。最后找到该放的位置之后再赋值
     *
     * @param arr 数组
     */
    public static void insertSortV2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j;
            for (j = i; j > 0 && arr[j - 1] > temp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
    }

    /**
     * 归并排序, 自顶向下
     *
     * @param arr 数组
     */
    public static void mergeSort(int[] arr) {
        doMergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 归并排序的处理
     *
     * @param arr   数组
     * @param start 数组开始的位置
     * @param end   数组结束的位置
     */
    private static void doMergeSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        doMergeSort(arr, start, mid);
        doMergeSort(arr, mid + 1, end);
        //如果不是已经排序好的数组则需要归并操作
        if (arr[mid] > arr[mid + 1])
            merge(arr, start, mid, end);
    }

    /**
     * 归并操作
     *
     * @param arr   数组
     * @param start 数组开始的位置
     * @param mid   数组中间值
     * @param end   数组结束的位置
     */
    private static void merge(int[] arr, int start, int mid, int end) {
        //另外需要开辟一个临时空间
        int[] aux = new int[end - start + 1];
        //复制临时空间
        if (end + 1 - start >= 0) {
            System.arraycopy(arr, start, aux, 0, end + 1 - start);
        }
        int i = start;
        int j = mid + 1;
        for (int k = start; k <= end; k++) {

            if (i > mid) {
                //处理边界问题。并且附带处理了奇数的问题。
                arr[k] = aux[j - start];
                j++;
            } else if (j > end) {
                arr[k] = aux[i - start];
                i++;
            } else if (aux[i - start] < aux[j - start]) {
                //真正的归并处理过程
                arr[k] = aux[i - start];
                i++;
            } else {
                arr[k] = aux[j - start];
                j++;
            }
        }
    }

    public static void quickSort(){

    }


    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    continue;
                }
                swap(arr, i, j);
            }
        }
    }

    /**
     * 堆排序
     *
     * @param arr 数组
     */
    public static void heapSort(int[] arr){
        MaxHeap maxHeap = new MaxHeap(arr);
        for(int index = arr.length - 1; index >= 0; index --){
            arr[index] = maxHeap.popMax();
        }
    }
}
