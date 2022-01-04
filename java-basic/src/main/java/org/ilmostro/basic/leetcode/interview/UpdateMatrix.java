package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.annotation.SolveError;

/**
 *
 * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
 *
 * 两个相邻元素间的距离为 1 。
 *
 *  
 *
 * 示例 1：
 *
 *
 *
 * 输入：mat = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：[[0,0,0],[0,1,0],[0,0,0]]
 * 示例 2：
 *
 *
 *
 * 输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
 * 输出：[[0,0,0],[0,1,0],[1,2,1]]
 *  
 *
 * 提示：
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 104
 * 1 <= m * n <= 104
 * mat[i][j] is either 0 or 1.
 * mat 中至少有一个 0 
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/01-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class UpdateMatrix {

    public static void main(String[] args) {
        int[][] mat = {{0,0,0}, {0,1,0},{1,1,1}};
        int[][] ints = new UpdateMatrix().updateMatrix(mat);
        for (int[] anInt : ints) {
            for (int i : anInt) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }

    @SolveError("想法是使用深度优先，但是解题为广度优先")
    public int[][] updateMatrix(int[][] mat) {
        if(mat == null || mat.length <= 0) return null;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                dfs(mat, i, j);
            }
        }
        return mat;
    }

    public int dfs(int[][] mat, int i, int j){
        if(i < 0 || i > mat.length - 1 || j < 0 || j > mat[0].length - 1) return 1;
        if (mat[i][j] == 0) return 0;
        int left = dfs(mat, i - 1, j);
        int right = dfs(mat, i + 1, j);
        int up = dfs(mat, i, j - 1);
        int down = dfs(mat, i, j + 1);
        mat[i][j] = Math.min(Math.min(Math.min(left, right), up), down) + 1;
        return mat[i][j];
    }

}
