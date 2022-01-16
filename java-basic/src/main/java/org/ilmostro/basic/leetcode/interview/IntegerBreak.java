package org.ilmostro.basic.leetcode.interview;

/**
 * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m-1] 。请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 * <p>
 * 示例 1：
 * <p>
 * 输入: 2
 * 输出: 1
 * 解释: 2 = 1 + 1, 1 × 1 = 1
 * 示例 2:
 * <p>
 * 输入: 10
 * 输出: 36
 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
 * 提示：
 * <p>
 * 2 <= n <= 58
 * 注意：本题与主站 343 题相同：https://leetcode-cn.com/problems/integer-break/
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jian-sheng-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class IntegerBreak {

    public static void main(String[] args) {
        int i = new IntegerBreak().cuttingRope(10);
        System.out.println(i);
    }

    public int cuttingRope(int n) {
        return dynamic(n);
    }

    public int math(int n) {
        if (n <= 3) return n - 1;
        int a = n / 3, b = n % 3;
        if (b == 0) return (int) Math.pow(3, a);
        if (b == 1) return (int) Math.pow(3, a - 1) * 4;
        return (int) Math.pow(3, a) * 2;
    }

    public int memory(int n, int[] memory){
        if (n == 1) return 1;
        if (memory[n] != -1) return memory[n];
        int max = -1;
        for (int i = 1; i < n - 1; i++) {
            // i + (n - i)
            max = Math.max(Math.max(i * memory(n - i, memory), max), i * (n - i));
        }
        memory[n] = max;
        return max;
    }

    public int dynamic(int n) {
        if (n <= 3) return n - 1;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for (int len = 4; len <= n; len++) {
            for (int k = 2; k < len - 1; k++) {
                dp[len] = Math.max(dp[len], dp[k] * dp[len - k]);
            }
        }
        return dp[n];
    }

    /**
     *
     * 优化之后的动态规划
     *
     * @param n 数字
     * @return 拆分之后的最大乘积
     */
    public int optimize(int n){
        if(n <= 2) return 1;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i - 1; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * dp[i - j], j *( i - j)));
            }
        }
        return dp[n];
    }

}
