package org.ilmostro.basic.algorithm.leetcode.dynamic;

/**
 * 三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。
 * <p>
 * 示例1:
 * <p>
 * 输入：n = 3
 * 输出：4
 * 说明: 有四种走法
 * 示例2:
 * <p>
 * 输入：n = 5
 * 输出：13
 * 提示:
 * <p>
 * n范围在[1, 1000000]之间
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/three-steps-problem-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ThreeStepsProblemLcci {

    public static void main(String[] args) {
        int i = new ThreeStepsProblemLcci().waysToStep(61);
        System.out.println(i);
    }

    public int waysToStep(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 4;
        long a = 1, b = 2, c = 4, d;
        while (--n >= 3) {
            d = a + b + c;
            a = b;
            b = c;
            c = d % 1000000007;
        }
        return (int) c;
    }
}
