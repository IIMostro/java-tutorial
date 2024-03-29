package org.ilmostro.basic.algorithm.leetcode.dynamic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
 * <p>
 * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * 输出：11
 * 解释：如下面简图所示：
 * 2
 * 3 4
 * 6 5 7
 * 4 1 8 3
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * 示例 2：
 * <p>
 * 输入：triangle = [[-10]]
 * 输出：-10
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= triangle.length <= 200
 * triangle[0].length == 1
 * triangle[i].length == triangle[i - 1].length + 1
 * -104 <= triangle[i][j] <= 104
 *  
 * <p>
 * 进阶：
 * <p>
 * 你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/triangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Triangle {

    public static void main(String[] args) {
        List<List<Integer>> lists = Arrays.asList(Collections.singletonList(2), Arrays.asList(3, 4), Arrays.asList(6, 5, 7), Arrays.asList(4, 1, 8, 3));

        /*
            triangle            dp
               2                 2
              3 4              5   6
             6 5 7           11  10  13
            4 1 8 3        15  11  18  16
         */
        int i = new Triangle().minimumTotal(lists);
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int[][] dp = new int[triangle.size()][triangle.size()];
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < triangle.size(); ++i) {
            //第一个只有上一个加当前
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
            //中间的元素则可以判断出上一个左边还是上一个相同位置的元素最小值
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
            }
            //最后一个也是只有上一个加当前
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }
        int min = dp[dp.length - 1][0];
        for (int i = 1; i < dp.length; ++i) {
            min = Math.min(min, dp[dp.length - 1][i]);
        }
        return min;
    }

    /**
     * 这是这道题目的递归解法，利用的就是 f(i, j) = min(f(i + 1, j), f(i + 1, j + 1)) + triangle[i][j]
     *
     * @param triangle 需要搜索的列表
     * @param i 行号
     * @param j 列号
     * @return 路径之和
     */
    public int dfs(List<List<Integer>> triangle, int i, int j){
        if (triangle.size() == i) return 0;
        // f(i, j) = min(f(i + 1, j), f(i + 1, j + 1)) + triangle[i][j]
        return Math.min(dfs(triangle, i + 1,j), dfs(triangle, i + 1, j+ 1)) + triangle.get(i).get(j);
    }

    /**
     * 利用记忆搜索的方式优化递归求解,
     *
     * @param triangle 需要搜索的列表
     * @param i 行号
     * @param j 列号
     * @param memory 记忆列表
     * @return 路径之和
     */
    public int memory(List<List<Integer>> triangle, int i, int j, int[][] memory){
        if(triangle.size() == i) return 0;
        if (memory[i][j] != -1) return memory[i][j];
        memory[i][j] = Math.min(memory(triangle, i + 1,j, memory), memory(triangle, i + 1, j+ 1, memory)) + triangle.get(i).get(j);
        return memory[i][j];
    }


    public int optimize(List<List<Integer>> triangle){
        int[] dp = new int[triangle.size()];
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < triangle.size(); i++) {
            dp[i] = dp[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + triangle.get(i).get(j);
            }
            dp[0] += triangle.get(i).get(0);
        }
        int min = dp[0];
        for (int i = 1; i < triangle.size(); ++i) {
            min = Math.min(min, dp[i]);
        }
        return min;
    }
}
