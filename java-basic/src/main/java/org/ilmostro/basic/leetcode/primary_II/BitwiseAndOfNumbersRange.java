package org.ilmostro.basic.leetcode.primary_II;

/**
 *
 * 给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：left = 5, right = 7
 * 输出：4
 * 示例 2：
 *
 * 输入：left = 0, right = 0
 * 输出：0
 * 示例 3：
 *
 * 输入：left = 1, right = 2147483647
 * 输出：0
 *  
 *
 * 提示：
 *
 * 0 <= left <= right <= 231 - 1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/bitwise-and-of-numbers-range
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class BitwiseAndOfNumbersRange {

    public int rangeBitwiseAnd(int left, int right) {
        int ans = 0;
        while(left < right){
            right >>=1;
            left >>= 1;
            ans++;
        }
        return right << ans;
    }
}
