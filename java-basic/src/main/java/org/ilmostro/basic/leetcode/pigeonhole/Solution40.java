package org.ilmostro.basic.leetcode.pigeonhole;

/**
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 *  
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author li.bowei
 */
public class Solution40 {

    public static void main(String[] args) {
        int[][] matrix = {
                {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        boolean numberIn2DArray = new Solution40().findNumberIn2DArray(matrix, 31);
        System.out.println(numberIn2DArray);
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix == null || matrix.length <= 0){
            return false;
        }
        for (int[] line : matrix) {
            int i1 = binarySearchFloorIndex(line, 0, line.length - 1, target);
            if (i1 >= 0) {
                return true;
            }
        }
        return false;
    }

    public int binarySearchFloorIndex(int[] nums, int l, int r, int target) {
        if (l > r) {
            return -1;
        }
        int mid = l + (r - l) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            return binarySearchFloorIndex(nums, l, mid - 1, target);
        } else {
            return binarySearchFloorIndex(nums, mid + 1, r, target);
        }
    }
}
