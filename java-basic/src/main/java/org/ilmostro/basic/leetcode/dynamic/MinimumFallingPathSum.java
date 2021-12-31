package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
 * <p>
 * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：matrix = [[2,1,3],[6,5,4],[7,8,9]]
 * 输出：13
 * 解释：下面是两条和最小的下降路径，用加粗+斜体标注：
 * [[2,1,3],      [[2,1,3],
 * [6,5,4],       [6,5,4],
 * [7,8,9]]       [7,8,9]]
 * 示例 2：
 * <p>
 * 输入：matrix = [[-19,57],[-40,-5]]
 * 输出：-59
 * 解释：下面是一条和最小的下降路径，用加粗+斜体标注：
 * [[-19,57],
 * [-40,-5]]
 * 示例 3：
 * <p>
 * 输入：matrix = [[-48]]
 * 输出：-48
 *  
 * <p>
 * 提示：
 * <p>
 * n == matrix.length
 * n == matrix[i].length
 * 1 <= n <= 100
 * -100 <= matrix[i][j] <= 100
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-falling-path-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MinimumFallingPathSum {

    public static void main(String[] args) {
        int[][] matrix = {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}};
        int i = new MinimumFallingPathSum().minFallingPathSum(matrix);
    }

    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        int[] dp = new int[n + 2];
        dp[0] = dp[n + 1] = Integer.MAX_VALUE;
        System.arraycopy(matrix[0], 0, dp, 1, n);
        for (int i = 1; i < m; i++) {
            int temp, last = Integer.MAX_VALUE;
            for (int j = 1; j <= n; j++) {
                temp = dp[j];
                dp[j] = Math.min(Math.min(last, dp[j]), dp[j + 1]) + matrix[i][j - 1];
                last = temp;
            }
        }
        int min = Integer.MAX_VALUE;
        for (int j = 1; j <= n; j++) min = Math.min(min, dp[j]);
        return min;
    }
}
