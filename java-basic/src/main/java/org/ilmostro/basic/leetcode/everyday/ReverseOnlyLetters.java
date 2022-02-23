package org.ilmostro.basic.leetcode.everyday;

/**
 * 给你一个字符串 s ，根据下述规则反转字符串：
 * <p>
 * 所有非英文字母保留在原有位置。
 * 所有英文字母（小写或大写）位置反转。
 * 返回反转后的 s 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "ab-cd"
 * 输出："dc-ba"
 * 示例 2：
 * <p>
 * 输入：s = "a-bC-dEf-ghIj"
 * 输出："j-Ih-gfE-dCba"
 * 示例 3：
 * <p>
 * 输入：s = "Test1ng-Leet=code-Q!"
 * 输出："Qedo1ct-eeLg=ntse-T!"
 *  
 * <p>
 * 提示
 * <p>
 * 1 <= s.length <= 100
 * s 仅由 ASCII 值在范围 [33, 122] 的字符组成
 * s 不含 '\"' 或 '\\'
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-only-letters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ReverseOnlyLetters {

    public static void main(String[] args) {
        String s = new ReverseOnlyLetters().reverseOnlyLetters("Test1ng-Leet=code-Q!");
        System.out.println(s);
    }

    public String reverseOnlyLetters(String s) {
        if (s == null || s.length() <= 1) return s;
        int left = 0, right = s.length() - 1;
        char[] ans = new char[s.length()];

        while (left <= right) {
            if (!Character.isLetter(s.charAt(left))) {
                ans[left] = s.charAt(left);
                left++;
                continue;
            }
            if (!Character.isLetter(s.charAt(right))) {
                ans[right] = s.charAt(right);
                right--;
                continue;
            }
            ans[left] = s.charAt(right);
            ans[right] = s.charAt(left);
            right--;
            left++;
        }
        return new String(ans);
    }
}
