package org.ilmostro.basic.leetcode.dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
 *
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 *
 *
 *  
 *
 * 示例 1:
 *
 * 输入: rowIndex = 3
 * 输出: [1,3,3,1]
 * 示例 2:
 *
 * 输入: rowIndex = 0
 * 输出: [1]
 * 示例 3:
 *
 * 输入: rowIndex = 1
 * 输出: [1,1]
 *  
 *
 * 提示:
 *
 * 0 <= rowIndex <= 33
 *  
 *
 * 进阶：
 *
 * 你可以优化你的算法到 O(rowIndex) 空间复杂度吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/pascals-triangle-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution30 {

    public static void main(String[] args) {
        List<Integer> row = new Solution30().getRow(15);
    }

    public List<Integer> getRow(int rowIndex) {
        return normal(rowIndex);
    }

    public List<Integer> normal(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);
        for (int i = 1; i <= rowIndex; ++i) {
            row.add(0);
            for (int j = i; j > 0; --j) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }
        return row;
    }
}
