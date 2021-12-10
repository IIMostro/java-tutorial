package org.ilmostro.basic.leetcode.interview;

/**
 * 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
 * <p>
 * 示例 1：
 * <p>
 * 输入: s = "leetcode"
 * 输出: false
 * 示例 2：
 * <p>
 * 输入: s = "abc"
 * 输出: true
 * 限制：
 * <p>
 * 0 <= len(s) <= 100
 * 如果你不使用额外的数据结构，会很加分。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/is-unique-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution1 {

    public static void main(String[] args) {
        boolean abc = new Solution1().isUnique("leetcode");
        System.out.println(abc);
    }

    public boolean isUnique(String astr) {
        if (astr == null || astr.length() <= 0) {
            return true;
        }
        int a = 0;
        for (int i = 0; i < astr.length(); i++) {
            int bit = astr.charAt(i) - 97;
            int wordBit = 1 << bit;
            if ((wordBit & a) == wordBit) {
                return false;
            } else {
                a |= wordBit;
            }
        }
        return true;
    }
}
