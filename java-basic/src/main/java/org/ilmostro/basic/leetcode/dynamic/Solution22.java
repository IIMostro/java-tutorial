package org.ilmostro.basic.leetcode.dynamic;

/**
 * 硬币。给定数量不限的硬币，币值为25分、10分、5分和1分，编写代码计算n分有几种表示法。(结果可能会很大，你需要将结果模上1000000007)
 * <p>
 * 示例1:
 * <p>
 * 输入: n = 5
 * 输出：2
 * 解释: 有两种方式可以凑成总金额:
 * 5=5
 * 5=1+1+1+1+1
 * 示例2:
 * <p>
 * 输入: n = 10
 * 输出：4
 * 解释: 有四种方式可以凑成总金额:
 * 10=10
 * 10=5+5
 * 10=5+1+1+1+1+1
 * 10=1+1+1+1+1+1+1+1+1+1
 * 说明：
 * <p>
 * 注意:
 * <p>
 * 你可以假设：
 * <p>
 * 0 <= n (总金额) <= 1000000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution22 {

    public static void main(String[] args) {
        int i = new Solution22().waysToChange(10);
        System.out.println(i);
    }

    public int waysToChange(int n) {
        int[] coins = {25, 10, 5, 1};
        int[] f = new int[n + 1];
        f[0] = 1;
        for (int c = 0; c < 4; c++) {
            int coin = coins[c];
            for (int i = coin; i <= n; i++) {
                f[i] = (f[i] + f[i - coin]) % 1000000007;
            }
        }
        return f[n];
    }
}
