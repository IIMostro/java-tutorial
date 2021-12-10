package org.ilmostro.basic.leetcode.pigeonhole;

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * <p>
 * 请必须使用时间复杂度为 O(log n) 的算法。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-insert-position
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/search-insert-position/}
 */
public class Solution1 {

    public static void main(String[] args) {
        int[] nums = new int[]{1};
        int i = new Solution1().searchInsert(nums, 0);
        System.out.println(i);
    }

    public int searchInsert(int[] nums, int target) {
        return doSearch(nums, 0, nums.length - 1, target);
    }

    /**
     * 简单的二分搜索，注意大于的情况需要处理边界问题
     *
     * @param nums   需要搜索的数组
     * @param l      数组左边界
     * @param r      数组右边界
     * @param target 需要搜索的目标
     * @return index
     */
    private int doSearch(int[] nums, int l, int r, int target) {
        if (l == r && nums[l] < target) {
            return l + 1;
        }
        if (l == r) {
            return l;
        }
        int mid = l + (r - l) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            return doSearch(nums, l, mid - 1, target);
        } else {
            return doSearch(nums, mid + 1, r, target);
        }
    }
}
