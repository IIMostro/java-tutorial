package org.ilmostro.basic.leetcode.interview;

/**
 * @author li.bowei
 */
public class FindPivotIndex {

    public static void main(String[] args) {
        int[] nums = {2,1,-1};
        int i = new FindPivotIndex().pivotIndex(nums);
        System.out.println(i);
    }

    public int pivotIndex(int[] nums) {
        if (nums == null || nums.length <= 0) return -1;
        int right = 0;
        for (int num : nums) {
            right+=num;
        }
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            right -= nums[i];
            if(left == right) return i;
            left += nums[i];
        }
        return -1;
    }
}
