package org.ilmostro.basic.algorithm.leetcode.top100;

import org.ilmostro.basic.annotation.Attention;
import org.ilmostro.basic.annotation.Middle;

import java.util.Arrays;

/**
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-equal-subset-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
@Middle
@Attention(algorithm = Attention.Algorithm.DYNAMIC, value = "转化为背包问题，但是我还是想不明白")
public class PartitionEqualSubsetSum {

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1};
        boolean b = new PartitionEqualSubsetSum().canPartition(nums);
    }

    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        if ((sum & 1) == 1) return false;
        int[][] memory = new int[n][sum / 2 + 1];
        for (int[] ints : memory) {
            Arrays.fill(ints, -1);
        }
        return partition(nums, memory, n - 1, sum / 2);
    }

    public boolean dynamic(int[] nums) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        if ((sum & 1) == 1) return false;
        int mid = sum / 2;
        boolean[][] dp = new boolean[n][mid + 1];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= mid; j++) {
                dp[i][j] = dp[i - 1][j];
                if (nums[i] == j) {
                    dp[i][j] = true;
                    continue;
                }
                if (nums[i] < j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[n - 1][mid];
    }

    public boolean optimize(int[] nums){
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        if ((sum & 1) == 1) return false;
        int mid = sum / 2;
        boolean[] dp = new boolean[mid + 1];
        Arrays.fill(dp, false);
        for (int i = 0; i <= mid; i++) {
            dp[i] = nums[i] == i;
        }
        for (int i = 1; i < n; i++) {
            for (int j = mid; j >= nums[i]; j--) {
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[mid];
    }

    public boolean partition(int[] nums, int[][] memory, int index, int sum) {
        if (sum == 0) return true;
        if (sum < 0 || index < 0) return false;
        if (memory[index][sum] != -1) return memory[index][sum] == 1;
        memory[index][sum] =  (partition(nums, memory, index - 1, sum) ||
                partition(nums, memory, index - 1, sum - nums[index])) ? 1 : 0;
        return memory[index][sum] == 1;
    }
}
