package org.ilmostro.basic.leetcode.dynamic;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 * <p>
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 2
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：n = 7
 * 输出：21
 * 示例 3：
 * <p>
 * 输入：n = 0
 * 输出：1
 * 提示：
 * <p>
 * 0 <= n <= 100
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/qing-wa-tiao-tai-jie-wen-ti-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *  青蛙跳台阶问题： f(0)=1f(0)=1 , f(1)=1f(1)=1 , f(2)=2f(2)=2
 *  f(n)=f(n−1)+f(n−2)
 *
 * @author li.bowei
 */
public class ForgNumWays {

    public static void main(String[] args) {
        int i = new ForgNumWays().numWays(7);
        System.out.println(i);
    }

    public int numWays(int n) {
        return dynamic(n);
    }

    public int dynamic(int n){
        if(n <2){
            return 1;
        }
        int a, b = 1, sum = 2;
        for(int i = 3; i <= n; i++){
            a = b;
            b = sum;
            sum = (a + b) % 1000000007;
        }
        return sum;
    }
}
