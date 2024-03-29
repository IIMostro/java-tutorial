package org.ilmostro.basic.algorithm.leetcode.primary;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 示例:
 * <p>
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 说明:
 * <p>
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/move-zeroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MoveZeroes {

    public static void main(String[] args) {
        int[] arr = {0, 1, 0, 3, 12};
        new MoveZeroes().moveZeroes(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 0) return;
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) swap(nums, i, left ++);
        }
    }

    public void swap(int[] nums, int i, int j) {
        if (i == j) return;
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

}
