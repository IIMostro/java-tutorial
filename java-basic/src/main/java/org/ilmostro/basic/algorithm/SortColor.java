package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class SortColor {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2, 1, 0, 1, 2, 0, 2, 1, 2, 0, 2, 1, 2};
        sort(nums);
        ArrayTestHelper.printArray(nums);
    }

    public static void sort(int[] nums) {
        int zero = -1;
        int two = nums.length;

        for (int i = 0; i < two; ) {

            if (nums[i] == 1) {
                i++;
            } else if (nums[i] == 2) {
                ArrayTestHelper.swap(nums, i, --two);
            } else {
                ArrayTestHelper.swap(nums, ++zero, i++);
            }
        }
    }
}
