package org.ilmostro.basic.leetcode.interview;

/**
 *
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 *
 * 输入为 非空 字符串且只包含数字 1 和 0。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: a = "11", b = "1"
 * 输出: "100"
 * 示例 2:
 *
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 *  
 *
 * 提示：
 *
 * 每个字符串仅由字符 '0' 或 '1' 组成。
 * 1 <= a.length, b.length <= 10^4
 * 字符串如果不是 "0" ，就都不含前导零。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-binary
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class AddBinary {

    public static void main(String[] args) {
        String s = new AddBinary().addBinary("10", "11");
        System.out.println(s);
    }

    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int length = Math.max(a.length(), b.length()), curr = 0;
        for (int i = 0; i < length; i++) {
            curr += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
            curr += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
            sb.append(curr % 2);
            curr /= 2; // 进位
        }
        if(curr > 0) sb.append('1');
        return sb.reverse().toString();
    }
}
