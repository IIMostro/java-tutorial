package org.ilmostro.basic.algorithm.leetcode.interview;

/**
 * 给定一个非空字符串 s，请判断如果 最多 从字符串中删除一个字符能否得到一个回文字符串。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "aba"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: s = "abca"
 * 输出: true
 * 解释: 可以删除 "c" 字符 或者 "b" 字符
 * 示例 3:
 * <p>
 * 输入: s = "abc"
 * 输出: false
 *  
 * <p>
 * 提示:
 * <p>
 * 1 <= s.length <= 105
 * s 由小写英文字母组成
 *  
 * <p>
 * 注意：本题与主站 680 题相同： https://leetcode-cn.com/problems/valid-palindrome-ii/
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/RQku0D
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ValidPalindrome_II {

    public boolean isPalindrome(String s) {
        return greed(s);
    }

    public boolean greed(String s) {
        if (s == null || s.length() <= 0) return true;
        int left = 0, right = s.length() - 1;
        while (left <= right) {
            char leftWord = s.charAt(left);
            char rightWord = s.charAt(right);
            if (leftWord == rightWord) {
                left++;
                right--;
            }else{
                return valid(s, left + 1, right) || valid(s, left, right - 1);
            }
        }
        return true;
    }

    public boolean valid(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
}
