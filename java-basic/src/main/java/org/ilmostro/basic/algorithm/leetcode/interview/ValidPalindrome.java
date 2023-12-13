package org.ilmostro.basic.algorithm.leetcode.interview;

/**
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * <p>
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 解释："amanaplanacanalpanama" 是回文串
 * 示例 2:
 * <p>
 * 输入: "race a car"
 * 输出: false
 * 解释："raceacar" 不是回文串
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 2 * 105
 * 字符串 s 由 ASCII 字符组成
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-palindrome
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ValidPalindrome {

    public static void main(String[] args) {
        boolean palindrome = new ValidPalindrome().isPalindrome("OP");
        System.out.println(palindrome);
    }

    public boolean isPalindrome(String s) {
        if (s == null || s.length() <= 0) return true;
        int left = 0, right = s.length() - 1;
        while (left <= right) {
            char c = s.charAt(left);
            char c1 = s.charAt(right);
            if (!Character.isLetterOrDigit(c)) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(c1)) {
                right--;
                continue;
            }
            if (Character.toLowerCase(c) != Character.toLowerCase(c1)) return false;
            left++;
            right--;
        }
        return true;
    }
}
