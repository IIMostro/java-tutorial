package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * <p>
 * 返回滑动窗口中的最大值。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sliding-window-maximum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SlidingWindowMaximum {

    public static void main(String[] args) {
        int[] ints = {1,3,-1,-3,5,3,6,7};
        int[] ints1 = new SlidingWindowMaximum().maxSlidingWindow(ints, 3);
        for (int i : ints1) {
            System.out.println(i);
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((v1, v2) -> v1[0] != v2[0] ? v2[0] - v1[0] : v2[1] - v1[1]);
        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        int[] result = new int[nums.length - k + 1];
        result[0] = queue.peek()[0];
        for (int i = k; i < nums.length; ++i) {
            queue.offer(new int[]{nums[i], i});
            while (queue.peek()[1] <= i - k) {
                queue.poll();
            }
            result[i - k + 1] = queue.peek()[0];
        }
        return result;
    }
}
