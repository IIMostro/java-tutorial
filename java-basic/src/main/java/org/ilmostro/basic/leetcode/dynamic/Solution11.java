package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: 12258
 * 输出: 5
 * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= num < 231
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution11 {

    public static void main(String[] args) {
        int number = 25;
        int i = new Solution11().translateNum(number);
        System.out.println(i);
    }

    public int translateNum(int num) {
        String value = String.valueOf(num);
        int p = 0, q = 0, r = 1;
        for (int i = 0; i < value.length(); i++) {
            p = q;
            q = r;
            r = q;
            if(i == 0){
                continue;
            }
            int curr = (value.charAt(i - 1) - '0') * 10 + (value.charAt(i) - '0');
            if(curr >= 10 && curr <= 25){
                r+=p;
            }
        }
        return r;
    }
}
