package org.ilmostro.basic.algorithm.leetcode.windows;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "aaabb", k = 3
 * 输出：3
 * 解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
 * 示例 2：
 *
 * 输入：s = "ababbc", k = 2
 * 输出：5
 * 解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
 *  
 *
 * 提示：
 *
 * 1 <= s.length <= 104
 * s 仅由小写英文字母组成
 * 1 <= k <= 105
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LongestSubstringWithAtLeastRepeatingCharacters {

    public static void main(String[] args) {
        int ans = new LongestSubstringWithAtLeastRepeatingCharacters().longestSubstring("aaabb", 3);
        System.out.println(ans);
    }

    public int longestSubstring(String s, int k) {
        if (s.length() < k) return 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }

        for (Character character : map.keySet()) {
            if (map.get(character) >= k) continue;
            int res = 0;
            for (String value : s.split(String.valueOf(character))) {
                res = Math.max(longestSubstring(value, k), res);
            }
            return res;
        }
        return s.length();
    }
}
