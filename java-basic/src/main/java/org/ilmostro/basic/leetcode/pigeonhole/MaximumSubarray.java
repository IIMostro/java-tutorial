package org.ilmostro.basic.leetcode.pigeonhole;

/**
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/maximum-subarray/}
 */
public class MaximumSubarray {

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int i = maxSubArray(nums);
        System.out.println(i);
    }

    public static int maxSubArray(int[] nums) {
        int pre = 0;
        int max = nums[0];
        for (int num : nums) {
            pre = Math.max((pre + num), num);
            max = Math.max(max, pre);
        }
        return max;
    }
}
