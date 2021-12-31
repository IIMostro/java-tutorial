package org.ilmostro.basic.leetcode.dfs;

/**
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 * <p>
 * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * <p>
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 * <p>
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * 输出：6
 * 解释：答案不应该是 11 ，因为岛屿只能包含水平或垂直这四个方向上的 1 。
 * 示例 2：
 * <p>
 * 输入：grid = [[0,0,0,0,0,0,0,0]]
 * 输出：0
 *  
 * <p>
 * 提示：
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * grid[i][j] 为 0 或 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-area-of-island
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MaxAreaOfIsland {

    public static void main(String[] args) {
        int[][] grid = {{1}};
        int i = new MaxAreaOfIsland().maxAreaOfIsland(grid);
        System.out.println(i);
    }

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length <= 0) return 0;
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                max = Math.max(dfs(grid, i, j), max);
            }
        }
        return max;
    }

    public int dfs(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i > grid.length - 1 || j > grid[0].length - 1 || grid[i][j] != 1) return 0;
        grid[i][j] = 0;
        return dfs(grid, i + 1, j) + dfs(grid, i - 1, j) + dfs(grid, i, j - 1) + dfs(grid, i, j + 1) + 1;
    }
}
