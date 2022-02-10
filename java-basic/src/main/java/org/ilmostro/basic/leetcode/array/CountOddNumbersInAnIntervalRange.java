package org.ilmostro.basic.leetcode.array;

/**
 *
 * 给你两个非负整数 low 和 high 。请你返回 low 和 high 之间（包括二者）奇数的数目。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：low = 3, high = 7
 * 输出：3
 * 解释：3 到 7 之间奇数数字为 [3,5,7] 。
 * 示例 2：
 *
 * 输入：low = 8, high = 10
 * 输出：1
 * 解释：8 到 10 之间奇数数字为 [9] 。
 *  
 *
 * 提示：
 *
 * 0 <= low <= high <= 10^9
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-odd-numbers-in-an-interval-range
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class CountOddNumbersInAnIntervalRange {

    public int countOdds(int low, int high) {
        if(low > high) return -1;
        if(low % 2 == 0 && high % 2 == 0) return (high - low) / 2;
        return (high - low) / 2 + 1;
    }
}
