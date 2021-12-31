package org.ilmostro.basic.leetcode.interview;

/**
 *
 * 给定两个字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
 *
 * 示例 1：
 *
 * 输入: s1 = "abc", s2 = "bca"
 * 输出: true
 * 示例 2：
 *
 * 输入: s1 = "abc", s2 = "bad"
 * 输出: false
 * 说明：
 *
 * 0 <= len(s1) <= 100
 * 0 <= len(s2) <= 100
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/check-permutation-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class CheckPermutation {

    public static void main(String[] args) {
        boolean b = new CheckPermutation().CheckPermutation("abc", "bad");
        System.out.println(b);
    }

    public boolean CheckPermutation(String s1, String s2) {
        if(s1.equals(s2)){
            return true;
        }
        if(s1.length() != s2.length()){
            return false;
        }

        int[] a = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            a[s1.charAt(i) - 97]++;
        }
        int[] b = new int[26];
        for (int i = 0; i < s2.length(); i++) {
            b[s2.charAt(i) - 97]++;
        }
        for (int i = 0; i < a.length; i++) {
            if(a[i] != b[i]){
                return false;
            }
        }
        return true;
    }
}
