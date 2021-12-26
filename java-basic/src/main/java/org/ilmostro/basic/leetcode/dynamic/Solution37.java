package org.ilmostro.basic.leetcode.dynamic;

/**
 *
 * 5. 最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 示例 2：
 *
 * 输入：s = "cbbd"
 * 输出："bb"
 * 示例 3：
 *
 * 输入：s = "a"
 * 输出："a"
 * 示例 4：
 *
 * 输入：s = "ac"
 * 输出："a"
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母（大写和/或小写）组成
 *
 * @author li.bowei
 */
public class Solution37 {

    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 0) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int center = center(s, i, i);
            int center1 = center(s, i, i + 1);

            int len = Math.max(center, center1);
            if(len > end - start){
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int center(String s, int left, int right){
        while(left >=0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }
}
