package org.ilmostro.basic.leetcode.interview;

/**
 * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
 * <p>
 * 两个相邻元素间的距离为 1 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：mat = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：[[0,0,0],[0,1,0],[0,0,0]]
 * 示例 2：
 * <p>
 * <p>
 * <p>
 * 输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
 * 输出：[[0,0,0],[0,1,0],[1,2,1]]
 *  
 * <p>
 * 提示：
 * <p>
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 104
 * 1 <= m * n <= 104
 * mat[i][j] is either 0 or 1.
 * mat 中至少有一个 0 
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/01-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class UpdateMatrix {

    public static void main(String[] args) {
        int[][] mat = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        int[][] ints = new UpdateMatrix().updateMatrix(mat);
        for (int[] anInt : ints) {
            for (int i : anInt) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }


    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = matrix[i][j] == 0 ? 0 : 10000;
            }
        }

        // 从左上角开始
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                }
                if (j - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                }
            }
        }
        // 从右下角开始
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i + 1 < m) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j] + 1);
                }
                if (j + 1 < n) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j + 1] + 1);
                }
            }
        }
        return dp;
    }

    public int dfs(int[][] mat, int i, int j) {
        if (i < 0 || i > mat.length - 1 || j < 0 || j > mat[0].length - 1) return 1;
        if (mat[i][j] == 0) return 0;
        int left = dfs(mat, i - 1, j);
        int right = dfs(mat, i + 1, j);
        int up = dfs(mat, i, j - 1);
        int down = dfs(mat, i, j + 1);
        mat[i][j] = Math.min(Math.min(Math.min(left, right), up), down) + 1;
        return mat[i][j];
    }

}
