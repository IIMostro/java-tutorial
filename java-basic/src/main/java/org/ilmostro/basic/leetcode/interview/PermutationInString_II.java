package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.annotation.Middle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * 438. 找到字符串中所有字母异位词
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 *
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 *
 *
 *
 * 示例 1:
 *
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 *  示例 2:
 *
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 *
 *
 * 提示:
 *
 * 1 <= s.length, p.length <= 3 * 104
 * s 和 p 仅包含小写字母
 *
 * 注意：本题与主站 438 题相同： https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/VabMRr
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
@Middle
public class PermutationInString_II {

    public static void main(String[] args) {
        List<Integer> anagrams = new PermutationInString_II().findAnagrams("cbaebabacd", "abc");
        for (Integer anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    public List<Integer> findAnagrams(String s, String p) {
        if(p.length() > s.length()) return Collections.emptyList();
        return windows(s, p);
    }

    public List<Integer> windows(String s, String p){
        int n = s.length(), m = p.length();
        int[] pattern = new int[26];
        int[] matching = new int[26];
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            pattern[p.charAt(i) - 'a']++;
            matching[s.charAt(i) - 'a']++;
        }
        if(Arrays.equals(pattern, matching)) result.add(0);
        for (int i = m; i < n; i++) {
            matching[s.charAt(i) - 'a'] ++;
            matching[s.charAt(i - m) - 'a']--;
            if(Arrays.equals(pattern, matching)) result.add(i - m + 1);
        }
        return result;
    }
}
