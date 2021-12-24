package org.ilmostro.basic.leetcode.pigeonhole;

/**
 * 请实现整数数字的乘法、减法和除法运算，运算结果均为整数数字，程序中只允许使用加法运算符和逻辑运算符，允许程序中出现正负常数，不允许使用位运算。
 * <p>
 * 你的实现应该支持如下操作：
 * <p>
 * Operations() 构造函数
 * minus(a, b) 减法，返回a - b
 * multiply(a, b) 乘法，返回a * b
 * divide(a, b) 除法，返回a / b
 * 示例：
 * <p>
 * Operations operations = new Operations();
 * operations.minus(1, 2); //返回-1
 * operations.multiply(3, 4); //返回12
 * operations.divide(5, -2); //返回-2
 * 提示：
 * <p>
 * 你可以假设函数输入一定是有效的，例如不会出现除法分母为0的情况
 * 单个用例的函数调用次数不会超过1000次
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/operations-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Operations {
    public Operations() {

    }

    public int minus(int a, int b) {
        return Math.subtractExact(a, b);
    }

    public int multiply(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    public int divide(int a, int b) {
        return Math.floorDiv(a, b);
    }
}
