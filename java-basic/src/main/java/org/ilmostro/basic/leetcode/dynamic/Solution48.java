package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
 * <p>
 * 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。
 * <p>
 * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 12
 * 输出：3
 * 解释：12 = 4 + 4 + 4
 * 示例 2：
 * <p>
 * 输入：n = 13
 * 输出：2
 * 解释：13 = 4 + 9
 *  
 * 提示：
 * <p>
 * 1 <= n <= 104
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/perfect-squares
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution48 {

    public static void main(String[] args) {
        int i = new Solution48().numSquares(12);
        System.out.println(i);
    }

    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int curr = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                curr = Math.min(curr, dp[i - j * j]);
            }
            dp[i] = curr + 1;
        }
        return dp[n];
    }

    public static boolean isPerfectSquare(long n) {
        if (n < 0)
            return false;
        long tst = (long) (Math.sqrt(n) + 0.5);
        return tst * tst == n;
    }
}
