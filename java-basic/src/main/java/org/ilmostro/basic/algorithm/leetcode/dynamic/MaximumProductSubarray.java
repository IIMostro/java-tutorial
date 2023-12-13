package org.ilmostro.basic.algorithm.leetcode.dynamic;

/**
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 * 示例 2:
 * <p>
 * 输入: [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-product-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MaximumProductSubarray {

    public static void main(String[] args) {

        int[] ints = {2, 3, -2, 4};

        int i = new MaximumProductSubarray().maxProduct(ints);
        System.out.println(i);
    }

    public int maxProduct(int[] nums) {
        int p = nums[0], q = nums[0], r = nums[0];
        int length = nums.length;
        for (int i = 1; i < length; ++i) {
            int mx = p, mn = q;
            p = Math.max(mx * nums[i], Math.max(nums[i], mn * nums[i]));
            q = Math.min(mn * nums[i], Math.min(nums[i], mx * nums[i]));
            r = Math.max(p, r);
        }
        return r;
    }
}
