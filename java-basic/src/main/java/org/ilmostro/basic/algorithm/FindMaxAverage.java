package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class FindMaxAverage {

    public double findMaxAverage(int[] nums, int k) {

        int max = 0;
        for (int i = 0; i < k; i++) {
            max += nums[i];
        }

        int curr = max;
        for (int i = 0; i < nums.length - k; i++) {
            curr = curr - nums[i] + nums[i + k];
            if (curr > max) {
                max = curr;
            }
        }

        return (double) max / k;
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

    public static void main(String[] args) {
        int[] nums = {4,2,1,3,3};
        double maxAverage = new FindMaxAverage().findMaxAverage(nums, 1);
        System.out.println(maxAverage);
    }

}
