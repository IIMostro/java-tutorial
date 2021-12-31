package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定一个由整数数组 A 表示的环形数组 C，求 C 的非空子数组的最大可能和。
 * <p>
 * 在此处，环形数组意味着数组的末端将会与开头相连呈环状。（形式上，当0 <= i < A.length 时 C[i] = A[i]，且当 i >= 0 时 C[i+A.length] = C[i]）
 * <p>
 * 此外，子数组最多只能包含固定缓冲区 A 中的每个元素一次。（形式上，对于子数组 C[i], C[i+1], ..., C[j]，不存在 i <= k1, k2 <= j 其中 k1 % A.length = k2 % A.length）
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：[1,-2,3,-2]
 * 输出：3
 * 解释：从子数组 [3] 得到最大和 3
 * 示例 2：
 * <p>
 * 输入：[5,-3,5]
 * 输出：10
 * 解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
 * 示例 3：
 * <p>
 * 输入：[3,-1,2,-1]
 * 输出：4
 * 解释：从子数组 [2,-1,3] 得到最大和 2 + (-1) + 3 = 4
 * 示例 4：
 * <p>
 * 输入：[3,-2,2,-3]
 * 输出：3
 * 解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3
 * 示例 5：
 * <p>
 * 输入：[-2,-3,-1]
 * 输出：-1
 * 解释：从子数组 [-1] 得到最大和 -1
 *  
 * <p>
 * 提示：
 * <p>
 * -30000 <= A[i] <= 30000
 * 1 <= A.length <= 30000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-sum-circular-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MaximumSumCircularSubarray {

    public static void main(String[] args) {
        MaximumSumCircularSubarray execute = new MaximumSumCircularSubarray();
        int i = execute.maxSubarraySumCircular(new int[]{3, -1, 2, -1});
        System.out.println(i);
    }

    public int maxSubarraySumCircular(int[] nums) {
        int p = nums[0], max = p, sum = p, min = 0;
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            p = nums[i] + Math.max(p, 0);
            max = Math.max(p, max);
        }
        p = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            p = nums[i] + Math.min(0, p);
            min = Math.min(p, min);
        }
        return Math.max(sum - min, max);
    }
}
