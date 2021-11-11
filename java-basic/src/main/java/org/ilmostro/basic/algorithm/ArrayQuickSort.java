package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

/**
 * @author li.bowei
 */
@Slf4j
public class ArrayQuickSort {

    public static void main(String[] args) {
        int[] ints = ArrayTestHelper.create(10, 0, 100);
        threeSort(ints, 0, ints.length - 1);
        ArrayTestHelper.printArray(ints);
    }

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void threeSort(int[] arr, int l, int r) {

        if (l >= r) {
            return;
        }

        int index = new Random().nextInt(r - l + 1) + l;
        int partition = arr[index];
        ArrayUtils.swap(arr, index, l);
        //arr[l+1 .. lt] 小于 partition
        //arr[lt + 1 .. k] 等于partition
        //arr[k + 1, gt-1] 都是没有进行排序的
        //arr[gt .. r] 大于partition
        int lt = l;
        int gt = r;
        int i = l + 1;
        while (i <= gt) {
            if (arr[i] < partition) {
                ArrayUtils.swap(arr, i++, ++lt);
            } else if (arr[i] > partition) {
                ArrayUtils.swap(arr, i, gt--);
            } else {
                i++;
            }
        }
        ArrayUtils.swap(arr, lt, l);
        threeSort(arr, l, lt - 1);
        threeSort(arr, gt, r);
    }

    public static void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int partition = partition2(arr, l, r);
        sort(arr, l, partition - 1);
        sort(arr, partition + 1, r);
    }

    /**
     * 单路快速排序寻找partition
     *
     * @param arr 数组
     * @param l   左边界
     * @param r   右边界
     * @return 排序好的partition
     */
    public static int partition(int[] arr, int l, int r) {
        int partition = new Random().nextInt(r - l + 1) + l;
        int value = arr[partition];
        int index = l + 1;
        ArrayUtils.swap(arr, l, partition);
        for (int i = l + 1; i < r + 1; i++) {
            if (arr[i] < value) {
                ArrayUtils.swap(arr, index++, i);
            }
        }
        ArrayUtils.swap(arr, index - 1, l);
        return index - 1;
    }

    /**
     * 双路快速排序
     *
     * @param arr 数组
     * @param l   左边界
     * @param r   右边界
     * @return 排好的partition
     */
    public static int partition2(int[] arr, int l, int r) {
        int partition = new Random().nextInt(r - l + 1) + l;
        int value = arr[partition];
        ArrayUtils.swap(arr, l, partition);
        int i = l + 1;
        int j = r;
        while (true) {

            //寻找 arr[i] > value的索引
            while (i <= r && arr[i] < value) {
                i++;
            }

            while (j >= l + 1 && arr[j] >= value) {
                j--;
            }

            if (i > j) {
                break;
            }

            ArrayUtils.swap(arr, i, j);
            i++;
            j--;
        }
        ArrayUtils.swap(arr, l, j);
        return j;
    }
}
