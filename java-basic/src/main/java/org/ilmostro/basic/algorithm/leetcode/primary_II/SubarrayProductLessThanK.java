package org.ilmostro.basic.algorithm.leetcode.primary_II;

/**
 * 给定一个正整数数组 nums和整数 k 。
 * <p>
 * 请找出该数组内乘积小于 k 的连续的子数组的个数。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [10,5,2,6], k = 100
 * 输出: 8
 * 解释: 8个乘积小于100的子数组分别为: [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]。
 * 需要注意的是 [10,5,2] 并不是乘积小于100的子数组。
 * 示例 2:
 * <p>
 * 输入: nums = [1,2,3], k = 0
 * 输出: 0
 *  
 * <p>
 * 提示: 
 * <p>
 * 1 <= nums.length <= 3 * 104
 * 1 <= nums[i] <= 1000
 * 0 <= k <= 106
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subarray-product-less-than-k
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SubarrayProductLessThanK {

    public static void main(String[] args) {
        int[] nums = {10, 9, 10, 4, 3, 8, 3, 3, 6, 2, 10, 10, 9, 3};
        new SubarrayProductLessThanK().numSubarrayProductLessThanK(nums, 19);
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        return pointer(nums, k);
    }

    public int windows(int[] nums, int k) {
        if (nums == null || nums.length <= 0) return 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            if (temp < k) sum++;
            for (int j = i + 1; j < nums.length; j++) {
                temp *= nums[j];
                if (temp < k) sum++;
                else break;
            }
        }
        return sum;
    }

    public int pointer(int[] nums, int k) {
        if (k <= 1) return 0;
        int temp = 1, sum = 0, left = 0;
        for (int i = 0; i < nums.length; i++) {
            temp *= nums[i];
            while(temp >= k) temp /=nums[left++];
            sum += i - left + 1;
        }
        return sum;
    }

}
