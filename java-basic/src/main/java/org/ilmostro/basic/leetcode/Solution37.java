package org.ilmostro.basic.leetcode;

/**
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 * <p>
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution37 {

    public static void main(String[] args) {
//        int[][] ints = new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        int[][] ints = new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        boolean b = new Solution37().searchMatrix(ints, 19);
        System.out.println(b);
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        //
        return false;
    }

    public int binarySearchFloorIndex(int[] nums, int l, int r, int target) {
        if (l > r) {
            return l;
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

    public int floor(int[] nums, int target) {
        int l = -1, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l + 1) / 2;
            if (nums[mid] >= target) {
                r = mid - 1;
            } else {
                l = mid;
            }
        }
        if (l + 1 < nums.length && nums[l + 1] == target) {
            return l + 1;
        }
        return l;
    }

}
