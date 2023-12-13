package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.Arrays;

/**
 * 从若干副扑克牌中随机抽 5 张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: True
 *  
 * <p>
 * 示例 2:
 * <p>
 * 输入: [0,0,1,2,5]
 * 输出: True
 *  
 * <p>
 * 限制：
 * <p>
 * 数组长度为 5 
 * <p>
 * 数组的数取值为 [0, 13] .
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/bu-ke-pai-zhong-de-shun-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class IsStraight {

    public static void main(String[] args) {
        int[] nums = {1, 4, 5, 0, 0};
        boolean straight = new IsStraight().isStraight(nums);
        System.out.println(straight);
    }

    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int zero = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zero++;
                continue;
            }
            if (i != 0 && nums[i - 1] != 0) {
                if (nums[i] - nums[i - 1] == 0) {
                    return false;
                } else if (nums[i] - nums[i - 1] > 1 && nums[i] - nums[i - 1] <= zero + 1) {
                    zero = zero + 1 - (nums[i] - nums[i - 1]);
                } else if (nums[i] - nums[i - 1] != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
