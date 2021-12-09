package org.ilmostro.basic.leetcode;

/**
 * 统计一个数字在排序数组中出现的次数。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/}
 */
public class Solution25 {

    public static void main(String[] args) {
        int[] ints = {1,1,2};
        int search = new Solution25().search(ints, 1);
        System.out.println(search);
    }

    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }

    public int binarySearch(int[] nums, int l, int r, int target) {
        if (l > r) {
            return 0;
        }
        int mid = l + (r - l) / 2;
        if (nums[mid] == target) {
            int size = 0;
            for (int i = mid; i < nums.length; i++) {
                if (nums[i] == target) {
                    size++;
                } else {
                    break;
                }
            }
            for (int i = mid - 1; i >= 0; i--) {
                if (nums[i] == target) {
                    size++;
                } else {
                    break;
                }
            }
            return size;
        } else if (nums[mid] < target) {
            return binarySearch(nums, mid + 1, r, target);
        } else {
            return binarySearch(nums, l, mid - 1, target);
        }
    }
}
