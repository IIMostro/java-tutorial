package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author li.bowei
 */
@Slf4j
public class ArrayMergeSort {

    public static void sort(int[] arr) {
        doSort(arr, 0, arr.length - 1);
    }

    public static void doSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        doSort(arr, l, mid);
        doSort(arr, mid + 1, r);
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, l, mid, r);
        }
    }

    public static void merge(int[] arr, int l, int mid, int r) {
        int[] temp = Arrays.copyOfRange(arr, l, r + 1);
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = temp[j - l];
                j++;
            } else if (j > r) {
                arr[k] = temp[i - l];
                i++;
            } else if (temp[i - l] > temp[j - l]) {
                arr[k] = temp[j - l];
                j++;
            } else {
                arr[k] = temp[i - l];
                i++;
            }
        }
    }

    public static void main(String[] args) {
        int[] ints = ArrayTestHelper.create(10, 0, 100);
        ArrayTestHelper.printArray(ints);
        sort(ints);
        ArrayTestHelper.printArray(ints);
    }
}
