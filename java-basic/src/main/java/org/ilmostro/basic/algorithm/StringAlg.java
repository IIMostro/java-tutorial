package org.ilmostro.basic.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * 示例 4:
 * <p>
 * 输入: s = ""
 * 输出: 0
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class StringAlg {

    public static void main(String[] args) {
        String name = "abcabcbb";
        int i = new StringAlg().lengthOfLongestSubstring(name);
        System.out.println(i);
    }

    public int lengthOfLongestSubstring(String s) {
        return optimized(s);
    }

    public int normal(String s){
        if (s.length() <= 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int max = 1;
        Set<Character> characters = new HashSet<>();
        for (int i = 0; i < chars.length && chars.length - i > max; i++) {
            for (int j = i; j < chars.length && j - i < max; j++) {
                if (characters.contains(chars[j])) {
                    break;
                }
                if (j - i >= max) max = j - i + 1;
                characters.add(chars[j]);
            }
            characters.clear();
        }
        return max;
    }

    public int optimized(String s) {
        if (s == null || s.length() <= 0) return 0;
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            //这里更新left其实就是对left + 1;
            if(map.containsKey(chars[i])) left = Math.max(left, map.get(chars[i]) + 1);
            map.put(chars[i], i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }
}
