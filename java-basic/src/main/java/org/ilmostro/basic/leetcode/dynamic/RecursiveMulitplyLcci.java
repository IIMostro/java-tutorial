package org.ilmostro.basic.leetcode.dynamic;

/**
 *
 * 递归乘法。 写一个递归函数，不使用 * 运算符， 实现两个正整数的相乘。可以使用加号、减号、位移，但要吝啬一些。
 *
 * 示例1:
 *
 *  输入：A = 1, B = 10
 *  输出：10
 * 示例2:
 *
 *  输入：A = 3, B = 4
 *  输出：12
 * 提示:
 *
 * 保证乘法范围不会溢出
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/recursive-mulitply-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class RecursiveMulitplyLcci {

    public static void main(String[] args) {
        int multiply = new RecursiveMulitplyLcci().multiply(2, 3);
        System.out.println(multiply);
    }

    public int multiply(int A, int B) {
        if(A == 0 || B == 0) {
            return 0;
        }
        if(A == 1){
            return B;
        }
        if(B == 1){
            return A;
        }
        return B + multiply(A - 1, B);
    }
}
