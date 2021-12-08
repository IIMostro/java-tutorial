package org.ilmostro.basic.leetcode;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * <p>
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 注意：给定 n 是一个正整数。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/climbing-stairs/}
 */
public class Solution17 {

    public static void main(String[] args) {
        int[] memo = new int[46];
        int i = new Solution17().climbStairs(45);
        System.out.println(i);
    }

    public int climbStairs(int n) {
        //这个返回1是代表如果只有一级楼梯的话就只有一种方法来爬
        if (n == 1) return 1;
        //这个返回2是2级楼梯有两种方法，爬两次一级加直接一次两级
        if (n == 2) return 2;
        if (n == 3) return 3;
        if (n == 4) return 5;
        if (n == 5) return 8;
        if (n == 6) return 13;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    public int climbStairs(int n, int[] memo) {
        if (memo[n] > 0) {
            return memo[n];
        }
        if (n == 1) {
            memo[1] = 1;
        } else if (n == 2) {
            memo[2] = 2;
        } else {
            memo[n] = climbStairs(n - 1, memo) + climbStairs(n - 2, memo);
        }
        return memo[n];
    }

    public int climbStairsN(int n) {
        int p, q = 0, r = 1;
        for (int i = 0; i < n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }
}
