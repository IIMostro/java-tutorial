package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import org.ilmostro.basic.annotation.Attention;

/**
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/house-robber
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class HouseRobber {

    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int rob = new HouseRobber().robV1(ints);
        System.out.println(rob);
    }

    public int rob(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[nums.length - 1];
    }

    public int robV1(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        int p = nums[0],r = Math.max(p, nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int temp = r;
            r = Math.max(p + nums[i], r);
            p = temp;
        }
        return r;
    }

    /**
     *
     * 自顶向下的递归实现
     *
     * @param nums 数组
     * @param memory 记忆数组
     * @param index 当前搜索的index
     * @return 最大值
     */
    @Attention(algorithm = Attention.Algorithm.DYNAMIC, value = "这里是自顶向下")
    public int recursion(int[] nums, int[] memory, int index){
        if (index >= nums.length) return 0;
        if(memory[index] != -1) return memory[index];
        int ans = 0;
        for (int i =index; i <nums.length; i++) {
            ans = Math.max(ans, nums[i] + recursion(nums, memory, i + 2));
        }
        memory[index] = ans;
        return ans;
    }

}
