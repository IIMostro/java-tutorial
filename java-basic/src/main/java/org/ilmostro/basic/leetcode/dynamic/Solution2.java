package org.ilmostro.basic.leetcode.dynamic;

/**
 *
 * 泰波那契序列 Tn 定义如下： 
 *
 * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
 *
 * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：n = 4
 * 输出：4
 * 解释：
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 * 示例 2：
 *
 * 输入：n = 25
 * 输出：1389537
 *  
 *
 * 提示：
 *
 * 0 <= n <= 37
 * 答案保证是一个 32 位整数，即 answer <= 2^31 - 1。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/n-th-tribonacci-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution2 {

    public static void main(String[] args) {
        int tribonacci = new Solution2().tribonacci(25);
        System.out.println(tribonacci);
    }

    public int tribonacci(int n) {

        int[] memory = new int[n + 1];
        return recursion(n, memory);
    }

    public int normal(int n){
        if(n <=0){
            return 0;
        }
        if(n < 3){
            return 1;
        }
        int p,q = 1,r = 1,z = 2;
        for (int i = 3; i < n; i++) {
            p = q;
            q = r;
            r = z;
            z = p + q + r;
        }
        return z;
    }

    public int recursion(int n, int[] memory){
        if(memory[n] > 0){
            return memory[n];
        }
        if(n <=0){
            return 0;
        }
        if(n < 3){
            return 1;
        }
        int result = recursion(n - 3, memory) + recursion(n - 2, memory) + recursion(n - 1, memory);
        memory[n] = result;
        return result;
    }
}
