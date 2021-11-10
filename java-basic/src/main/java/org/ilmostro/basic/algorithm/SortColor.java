package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class SortColor {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2, 1, 0, 1, 2, 0, 2, 1, 2, 0, 2, 1, 2};
        sort1(nums);
        ArrayTestHelper.printArray(nums);
    }

    public static void sort(int[] nums) {
        int zero = -1;
        int two = nums.length;

        for (int i = 0; i < two; ) {
            if (nums[i] == 1) {
                i++;
            } else if (nums[i] == 2) {
                swap(nums, i, --two);
            } else {
                swap(nums, ++zero, i++);
            }
        }
    }

    public static void sort1(int[] arr) {
        int zero = -1;
        int two = arr.length;
        for (int i = 0; i < two;) {
            if (arr[i] < 1) {
                swap(arr, ++zero, i++);
            } else if (arr[i] > 1) {
                swap(arr, i, --two);
            } else {
                i++;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
