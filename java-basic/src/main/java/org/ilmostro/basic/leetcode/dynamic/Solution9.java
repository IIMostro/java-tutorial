package org.ilmostro.basic.leetcode.dynamic;

/**
 * @author li.bowei
 */
public class Solution9 {

    public static void main(String[] args) {
        int i = new Solution9().maxValue(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}});
        System.out.println(i);
    }

    public int maxValue(int[][] grid) {

        if (grid == null || grid.length <= 0) return 0;
        int x = grid.length;
        int y = grid[0].length;

        for (int i = 0; i < x;i++) {
            for (int j = 0; j < y;j++) {
                if(i==0 && j ==0){
                    continue;
                }
                if(i == 0){
                    grid[i][j] = grid[i][j] + grid[i][j - 1];
                }else if(j==0){
                    grid[i][j] = grid[i][j] + grid[i - 1][j];
                }else{
                    grid[i][j] = Math.max(grid[i][j] + grid[i][j - 1], grid[i][j] + grid[i - 1][j]);
                }
            }
        }
        return grid[x - 1][y - 1];
    }
}
