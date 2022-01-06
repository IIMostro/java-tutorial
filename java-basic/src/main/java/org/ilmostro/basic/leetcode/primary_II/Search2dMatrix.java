package org.ilmostro.basic.leetcode.primary_II;

/**
 *
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 *
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 *  
 *
 * 示例 1：
 *
 *
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * 输出：true
 * 示例 2：
 *
 *
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * 输出：false
 *  
 *
 * 提示：
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -104 <= matrix[i][j], target <= 104
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Search2dMatrix {

    public static void main(String[] args) {

    }

    public boolean searchMatrix(int[][] matrix, int target) {
        return normal(matrix, target);
    }

    public boolean normal(int[][]matrix, int target){
        if(matrix == null || matrix.length == 0) return false;
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] nums = matrix[i];
            if(nums == null || nums.length <= 0) continue;
            if (nums[0] > target) {
                continue;
            }
            return doBinarySearch(nums, target, 0, nums.length - 1);
        }
        return false;
    }

    public boolean doBinarySearch(int[] nums, int target, int l, int r) {
        if (l > r) return false;
        int mid = l + (r - l) / 2;
        if (nums[mid] == target) return true;
        else if (nums[mid] > target) return doBinarySearch(nums, target, l, mid - 1);
        else return doBinarySearch(nums, target, mid + 1, r);
    }
}
