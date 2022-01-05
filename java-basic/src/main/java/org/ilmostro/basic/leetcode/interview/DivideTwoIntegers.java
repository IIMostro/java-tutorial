package org.ilmostro.basic.leetcode.interview;

/**
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 * <p>
 * 返回被除数 dividend 除以除数 divisor 得到的商。
 * <p>
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
 * 示例 2:
 * <p>
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = truncate(-2.33333..) = -2
 *  
 * <p>
 * 提示：
 * <p>
 * 被除数和除数均为 32 位有符号整数。
 * 除数不为 0。
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。本题中，如果除法结果溢出，则返回 231 − 1。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/divide-two-integers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class DivideTwoIntegers {

    public int divide(int dividend, int divisor) {
        if (dividend == 0) return 0;
        if (divisor == 1) return dividend;
        if (divisor == -1) {
            if (dividend > Integer.MIN_VALUE) return -dividend;
            return Integer.MAX_VALUE;
        }
        long a = dividend;
        long b = divisor;
        int sign = 1;
        if ((a > 0 && b < 0) || (a < 0 && b > 0)) {
            sign = -1;
        }
        a = a > 0 ? a : -a;
        b = b > 0 ? b : -b;
        int res = div(a, b);
        if (sign > 0) return Math.min(res, Integer.MAX_VALUE);
        return (int) -res;
    }

    public int div(long a, long b) {
        if (a < b) return 0;
        long count = 1;
        long tb = b;
        while ((tb + tb) <= a) {
            count = count + count;
            tb = tb + tb;
        }
        return (int) count + div(a - tb, b);
    }
}
