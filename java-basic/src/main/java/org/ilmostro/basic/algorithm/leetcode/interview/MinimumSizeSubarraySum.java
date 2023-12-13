package org.ilmostro.basic.algorithm.leetcode.interview;

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 target 。
 * <p>
 * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：target = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
 * 示例 2：
 * <p>
 * 输入：target = 4, nums = [1,4,4]
 * 输出：1
 * 示例 3：
 * <p>
 * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
 * 输出：0
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= target <= 109
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 105
 *  
 * <p>
 * 进阶：
 * <p>
 * 如果你已经实现 O(n) 时间复杂度的解法, 请尝试设计一个 O(n log(n)) 时间复杂度的解法。
 *  
 * <p>
 * 注意：本题与主站 209 题相同：https://leetcode-cn.com/problems/minimum-size-subarray-sum/
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/2VG8Kg
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MinimumSizeSubarraySum {

    public static void main(String[] args) {
        int[] arr = {12, 28, 83, 4, 25, 26, 25, 2, 25, 25, 25, 12};
        new MinimumSizeSubarraySum().minSubArrayLen(213, arr);
    }

    public int minSubArrayLen(int target, int[] nums) {
        return windows(target, nums);
    }

    public int windows(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int ans = Integer.MAX_VALUE, start = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum >= target) {
                ans = Math.min(ans, i - start + 1);
                sum -= nums[start];
                start++;
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}
