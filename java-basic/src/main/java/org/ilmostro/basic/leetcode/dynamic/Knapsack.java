package org.ilmostro.basic.leetcode.dynamic;

import org.ilmostro.basic.annotation.Attention;

/**
 * 0,1背包问题
 *
 * goods = [
 *       goods[][0] = 物品的重量，goods[][1] = 物品的价值
 *      [1,3],
 *      [2,3]
 * ]
 *
 * @author li.bowei
 */
@Attention(solution = Attention.Solution.DYNAMIC, value = "01背包问题，经典")
public class Knapsack {

    public int knapsack(int[][] goods, int capacity) {
        return dfs(goods, capacity, goods.length);
    }

    /**
     * 普通的自顶向下搜索
     *
     * @param goods 物品
     * @param capacity 背包的容积
     * @param index 当前搜索到的物品Index
     * @return 背包最优解
     */
    public int dfs(int[][] goods, int capacity, int index) {
        if (index < 0 || capacity <= 0) return 0;
        int ans = dfs(goods, capacity, index - 1);
        if(capacity >= goods[index][0])
            ans = Math.max(ans, goods[index][1] + dfs(goods, capacity - goods[index][0], index - 1));
        return ans;
    }

    /**
     * 带记忆的搜索
     *
     * @param goods 物品
     * @param memory 记忆数组
     * @param capacity 背包容积
     * @param index 物品Index
     * @return 最优解
     */
    public int dfs(int[][] goods, int[][] memory, int capacity, int index){
        if (index < 0 || capacity <= 0) return 0;
        if(memory[index][capacity] != -1) return memory[index][capacity];
        int ans = dfs(goods, memory, capacity, index - 1);
        if(capacity >= goods[index][0])
            ans = Math.max(ans, goods[index][1] + dfs(goods, memory,capacity - goods[index][0], index - 1));
        memory[index][capacity] = ans;
        return ans;
    }

    /**
     * 动态规划解决问题
     *
     * @param goods 物品
     * @param capacity 容积
     * @return 最大值
     */
    public int dynamic(int[][] goods, int capacity){
        if(goods == null || goods.length <= 0) return 0;
        int[][] dp = new int[goods.length][capacity + 1];
        for (int i = 0; i < capacity; i++) {
            dp[0][i] = i > goods[0][0] ? goods[0][1] : 0;
        }
        for (int i = 1; i < goods.length; i++) {
            for (int j = 1; j < capacity; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= goods[i][0]) dp[i][j] = Math.max(dp[i][j], goods[i][1] + dp[i - 1][j - goods[i][0]]);
            }
        }
        return dp[goods.length - 1][capacity];
    }

    /**
     * 使用两个数组的空间来解决上面的问题
     *
     * @param goods 物品
     * @param capacity 容积
     * @return 最优解
     */
    public int optimize(int[][] goods, int capacity){
        if(goods == null || goods.length <= 0) return 0;
        int[][] dp = new int[2][capacity + 1];
        for (int i = 0; i < capacity; i++) {
            dp[0][i] = i > goods[0][0] ? goods[0][1] : 0;
        }
        for (int i = 1; i < goods.length; i++) {
            for (int j = 1; j < capacity; j++) {
                dp[i % 2][j] = dp[(i - 1) % 2][j];
                if (j >= goods[i][0]) dp[i % 2][j] = Math.max(dp[i % 2][j], goods[i][1] + dp[(i - 1) % 2][j - goods[i][0]]);
            }
        }
        return dp[(goods.length - 1) % 2][capacity];
    }


    public int optimizeV1(int[][] goods, int capacity){
        if(goods == null || goods.length <= 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < capacity; i++) {
            dp[i] = i > goods[0][0] ? goods[0][1] : 0;
        }
        for (int i = 1; i < goods.length; i++) {
            //终止条件为当前的物品容积要小于当前背包所能容下的体积
            for (int j = capacity; j >= goods[i][0]; j-- ) {
                dp[j] = Math.max(dp[j], goods[i][1] + dp[j - goods[i][0]]);
            }
        }
        return dp[capacity];
    }

}
