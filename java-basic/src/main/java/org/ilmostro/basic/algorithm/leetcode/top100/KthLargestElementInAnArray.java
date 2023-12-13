package org.ilmostro.basic.algorithm.leetcode.top100;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * <p>
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 * <p>
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= k <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class KthLargestElementInAnArray {

    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 6, 4, 3};
        int k = new KthLargestElementInAnArray().findK(nums, 4);
        System.out.println(k);
    }

    public int findKthLargest(int[] nums, int k) {
        return normal(nums, k);
    }

    public int normal(int[] nums, int k) {
        return (int) IntStream.of(nums).boxed().sorted(Comparator.reverseOrder()).distinct().toArray()[k - 1];
    }

    public int findK(int[] nums, int k) {
        return doFindK(nums, 0, nums.length, k);
    }

    public int doFindK(int[] nums, int l, int r, int k) {
        int partition = partition(nums, l, r);

        if (nums.length - partition == k) {
            return nums[partition];
        } else if (nums.length - partition < k) {
            return doFindK(nums, l, partition, k);
        } else {
            return doFindK(nums, partition + 1, r, k);
        }
    }

    public int partition(int[] nums, int l, int r) {
        int value = nums[l];
        int index = l + 1;
        for (int i = l + 1; i < r; i++) {
            if (nums[i] < value) {
                swap(nums, i, index++);
            }
        }
        swap(nums, l, index - 1);
        return index - 1;
    }

    public void swap(int[] nums, int i, int j) {
        if (i == j) return;
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }
}
