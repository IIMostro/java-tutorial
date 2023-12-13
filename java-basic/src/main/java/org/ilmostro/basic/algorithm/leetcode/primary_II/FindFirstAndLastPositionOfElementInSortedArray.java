package org.ilmostro.basic.algorithm.leetcode.primary_II;

/**
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 * <p>
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * <p>
 * 进阶：
 * <p>
 * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例 2：
 * <p>
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * 示例 3：
 * <p>
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * nums 是一个非递减数组
 * -109 <= target <= 109
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class FindFirstAndLastPositionOfElementInSortedArray {

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int[] ints = new FindFirstAndLastPositionOfElementInSortedArray().searchRange(nums, 8);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public int[] searchRange(int[] nums, int target) {
        return binarySearch(nums, target);
    }

    public int[] binarySearch(int[] nums, int target) {
        int index = doBinarySearch(nums, target, 0, nums.length - 1);
        if (index == -1) return new int[]{-1, -1};
        int left = index;
        int right = index;
        while (left >= 0 && nums[left] == target) left--;
        while (right <= nums.length - 1 && nums[right] == target) {
            right++;
        }
        return new int[]{left + 1, right - 1};
    }

    public int doBinarySearch(int[] nums, int target, int l, int r) {
        if (l > r) return -1;
        int mid = l + (r - l) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] > target) return doBinarySearch(nums, target, l, mid - 1);
        else return doBinarySearch(nums, target, mid + 1, r);
    }
}
