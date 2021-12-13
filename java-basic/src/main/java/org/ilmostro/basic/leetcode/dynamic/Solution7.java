package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
 * <p>
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * <p>
 * 假设你总是可以到达数组的最后一个位置。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 * 示例 2:
 * <p>
 * 输入: nums = [2,3,0,1,4]
 * 输出: 2
 *  
 * <p>
 * 提示:
 * <p>
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 1000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jump-game-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution7 {

    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        int jump = new Solution7().jump(nums);
        System.out.println(jump);
    }

    public int jump(int[] nums) {
        int step = 0, end = 0, now = 0;
        int pos = 0;
        while (end < nums.length - 1) {
            pos = Math.max(pos, nums[now] + now);
            if (now == end) {
                step++;
                end = pos;
            }
            now++;
        }
        return step;
    }
}
