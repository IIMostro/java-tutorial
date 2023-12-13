package org.ilmostro.basic.algorithm.leetcode.dynamic;

/**
 * 有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。例如，前几个数按顺序应该是 1，3，5，7，9，15，21。
 * <p>
 * 示例 1:
 * <p>
 * 输入: k = 5
 * <p>
 * 输出: 9
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/get-kth-magic-number-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class GetKthMagicNumberLcci {

    public static void main(String[] args) {
        int kthMagicNumber = new GetKthMagicNumberLcci().getKthMagicNumber(5);
        System.out.println(kthMagicNumber);
    }

    public int getKthMagicNumber(int k) {
        int[] dp = new int[k];
        dp[0] = 1;
        int i = 0, j = 0, l = 0;
        for (int m = 1; m < k; m++) {
            int v1 = dp[i] * 3, v2 = dp[j] * 5, v3 = dp[l] * 7;
            dp[m] = Math.min(v1, Math.min(v2, v3));
            if (dp[m] == v1) i++;
            if (dp[m] == v2) j++;
            if (dp[m] == v3) l++;
        }
        return dp[k - 1];
    }
}
