package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

/**
 * 给定一个整数，写一个函数来判断它是否是 4 的幂次方。如果是，返回 true ；否则，返回 false 。
 * <p>
 * 整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4x
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/power-of-four
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/power-of-four/}
 */
public class PowerOfFour {

    public static void main(String[] args) {
        System.out.println(isPowerOfFour(63));
    }

    public static boolean isPowerOfFour(int n) {
        if (n == 1) {
            return true;
        }
        if (n < 4) {
            return false;
        }
        return n % 4 == 0 && isPowerOfFour(n >> 2);
    }
}
