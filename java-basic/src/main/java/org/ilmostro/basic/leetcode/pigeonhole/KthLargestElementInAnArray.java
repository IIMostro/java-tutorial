package org.ilmostro.basic.leetcode.pigeonhole;

/**
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * <p>
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class KthLargestElementInAnArray {

    public static void main(String[] args) {
        int[] ints = {5, 2, 3, 4, 6, 7};
        int partition = new KthLargestElementInAnArray().findKthLargest(ints, 6);
        System.out.println(partition);
    }

    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length <= 0 || nums.length < k) {
            return -1;
        }
        return partition(nums, 0, nums.length - 1, k);
    }

    public int partition(int[] nums, int l, int r, int target) {
        if (l >= r) {
            return l;
        }
        int partition = nums[l];
        int index = l + 1;
        for (int i = l + 1; i < r + 1; i++) {
            if(partition > nums[i]){
                swap(nums, i, index++);
            }
        }
        swap(nums, l, index - 1);
        if(nums.length - index == target){
            return nums[index - 1];
        }else if(nums.length - index > target){
            return partition(nums, index, r, target);
        }else{
            return partition(nums, l, index - 2, target);
        }
    }

    public void swap(int[] nums, int x, int y) {
        if(x == y){
            return;
        }
        nums[x] = nums[x] ^ nums[y];
        nums[y] = nums[x] ^ nums[y];
        nums[x] = nums[x] ^ nums[y];
    }
}
