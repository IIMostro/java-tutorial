package org.ilmostro.basic.algorithm.leetcode.everyday;

import java.util.Arrays;

/**
 * Alice 手中有一把牌，她想要重新排列这些牌，分成若干组，使每一组的牌数都是 groupSize ，并且由 groupSize 张连续的牌组成。
 * <p>
 * 给你一个整数数组 hand 其中 hand[i] 是写在第 i 张牌，和一个整数 groupSize 。如果她可能重新排列这些牌，返回 true ；否则，返回 false 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
 * 输出：true
 * 解释：Alice 手中的牌可以被重新排列为 [1,2,3]，[2,3,4]，[6,7,8]。
 * 示例 2：
 * <p>
 * 输入：hand = [1,2,3,4,5], groupSize = 4
 * 输出：false
 * 解释：Alice 手中的牌无法被重新排列成几个大小为 4 的组。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= hand.length <= 104
 * 0 <= hand[i] <= 109
 * 1 <= groupSize <= hand.length
 *  
 * <p>
 * 注意：此题目与 1296 重复：https://leetcode-cn.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/hand-of-straights
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class HandOfStraights {

    public boolean isNStraightHand(int[] hand, int groupSize) {
        Arrays.sort(hand);
        int i = 0, j = hand.length - 1;
        while (i < j) {
            hand[i] ^= hand[j];
            hand[j] ^= hand[i];
            hand[i] ^= hand[j];
            i++;
            j--;
        }
        for (int m = 0; m < hand.length; m++) {
            if (hand[m] < 0) continue;
            int count = 0;
            for (int n = m + 1; n < hand.length && count != groupSize - 1; n++) {
                if (hand[n] - hand[m] == count + 1) {
                    count++;
                    hand[n] = -1;
                }
            }
            if (count != groupSize - 1) return false;
        }
        return true;
    }
}
