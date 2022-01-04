package org.ilmostro.basic.leetcode.interview;

import java.util.Arrays;

/**
 * @author li.bowei
 */
public class ReversePairs {

    public static void main(String[] args) {
        int[] nums = {7, 5, 6, 4};
        int i = new ReversePairs().reversePairs(nums);
        System.out.println();
    }

    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public int mergeSort(int[] nums, int l, int r) {
        if (l >= r) return 0;
        int mid = l + (r - l) / 2;
        int sum = mergeSort(nums, l, mid) + mergeSort(nums, mid + 1, r);
        int[] temp = Arrays.copyOfRange(nums, l, r + 1);
        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i == mid + 1) {
                nums[k] = temp[j - l];
                j++;
            } else if (j == r + 1 || temp[i - l] <= temp[j - l]) {
                nums[k] = temp[i - l];
                i++;
            } else {
                nums[k] = temp[j - l];
                j++;
                sum += mid - i + 1;
            }
        }
        return sum;
    }
}
