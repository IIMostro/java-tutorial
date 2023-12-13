package org.ilmostro.basic.algorithm.leetcode.everyday;

/**
 * 给你一个 下标从 0 开始 的整数数组 nums ，返回满足下述条件的 不同 四元组 (a, b, c, d) 的 数目 ：
 * <p>
 * nums[a] + nums[b] + nums[c] == nums[d] ，且
 * a < b < c < d
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,3,6]
 * 输出：1
 * 解释：满足要求的唯一一个四元组是 (0, 1, 2, 3) 因为 1 + 2 + 3 == 6 。
 * 示例 2：
 * <p>
 * 输入：nums = [3,3,6,4,5]
 * 输出：0
 * 解释：[3,3,6,4,5] 中不存在满足要求的四元组。
 * 示例 3：
 * <p>
 * 输入：nums = [1,1,1,3,5]
 * 输出：4
 * 解释：满足要求的 4 个四元组如下：
 * - (0, 1, 2, 3): 1 + 1 + 1 == 3
 * - (0, 1, 3, 4): 1 + 1 + 3 == 5
 * - (0, 2, 3, 4): 1 + 1 + 3 == 5
 * - (1, 2, 3, 4): 1 + 1 + 3 == 5
 *  
 * <p>
 * 提示：
 * <p>
 * 4 <= nums.length <= 50
 * 1 <= nums[i] <= 100
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-special-quadruplets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class CountSpecialQuadruplets {

    public static void main(String[] args) {
        int[] arr = {1,2,3,6};
        int i = new CountSpecialQuadruplets().countQuadruplets(arr);
        System.out.println(i);
    }

    public int countQuadruplets(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length - 3; i++) {
            int sum = nums[i];
            for (int j = i + 1; j < i + 4; j++) {
                if (nums[j - 1] > nums[j]) break;
                if (j == i + 3 && nums[j] == sum) {
                    max++;
                    break;
                }
                sum += nums[j];
            }
        }
        return max;
    }
}
