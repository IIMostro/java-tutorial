package org.ilmostro.basic.algorithm.leetcode.dynamic;

/**
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 * <p>
 * 要求时间复杂度为O(n)。
 * <p>
 *  
 * <p>
 * 示例1:
 * <p>
 * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= arr.length <= 10^5
 * -100 <= arr[i] <= 100
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MaxSubArray {

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int i = new MaxSubArray().maxSubArray(nums);
        System.out.println(i);
    }

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length <= 0) return 0;
        int p = 0, r = nums[0];
        for (int num : nums) {
            //f(i)=max{f(i−1)+nums[i],nums[i]} 这就是这个题目的转移方程
            p = Math.max(p + num, num);
            r = Math.max(p, r);
        }
        return r;
    }
}
