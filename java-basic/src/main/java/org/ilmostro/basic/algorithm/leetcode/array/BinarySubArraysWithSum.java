package org.ilmostro.basic.algorithm.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个二元数组 nums ，和一个整数 goal ，请你统计并返回有多少个和为 goal 的 非空 子数组。
 * <p>
 * 子数组 是数组的一段连续部分。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,0,1,0,1], goal = 2
 * 输出：4
 * 解释：
 * 有 4 个满足题目要求的子数组：[1,0,1]、[1,0,1,0]、[0,1,0,1]、[1,0,1]
 * 示例 2：
 * <p>
 * 输入：nums = [0,0,0,0,0], goal = 0
 * 输出：15
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 3 * 104
 * nums[i] 不是 0 就是 1
 * 0 <= goal <= nums.length
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-subarrays-with-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class BinarySubArraysWithSum {

    public static void main(String[] args) {
        int[] nums = {0,0,0,0,0};
        int i = new BinarySubArraysWithSum().numSubarraysWithSum(nums, 0);
    }

    public int numSubarraysWithSum(int[] nums, int goal) {
        if (nums == null || nums.length <= 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0, ans = 0;
        for (int num : nums) {
            map.put(sum, map.getOrDefault(sum, 0) + 1);
            sum += num;
            ans += map.getOrDefault(sum - goal, 0);
        }
        return ans;
    }
}
