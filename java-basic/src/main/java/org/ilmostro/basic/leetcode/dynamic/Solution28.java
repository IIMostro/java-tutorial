package org.ilmostro.basic.leetcode.dynamic;

/**
 *
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 *
 *  
 *
 * 示例 1：
 *
 *
 * 输入：n = 3
 * 输出：5
 * 示例 2：
 *
 * 输入：n = 1
 * 输出：1
 *  
 *
 * 提示：
 *
 * 1 <= n <= 19
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-binary-search-trees
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution28 {


    public int numTrees(int n) {
        return dynamic(n);
    }

    public int dynamic(int n){
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    public int single(int n){
        switch(n){
            case 1: return 1;
            case 2: return 2;
            case 3: return 5;
            case 4: return 14;
            case 5: return 42;
            case 6: return 132;
            case 7: return 429;
            case 8: return 1430;
            case 9: return 4862;
            case 10: return 16796;
            case 11: return 58786;
            case 12: return 208012;
            case 13: return 742900;
            case 14: return 2674440;
            case 15: return 9694845;
            case 16: return 35357670;
            case 17: return 129644790;
            case 18: return 477638700;
            case 19: return 1767263190;
            default: return 0;
        }
    }
}
