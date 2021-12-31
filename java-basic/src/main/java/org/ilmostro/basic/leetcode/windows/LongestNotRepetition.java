package org.ilmostro.basic.leetcode.windows;

import java.util.HashMap;
import java.util.Map;

/**
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *  
 * <p>
 * 提示：
 * <p>
 * s.length <= 40000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zui-chang-bu-han-zhong-fu-zi-fu-de-zi-zi-fu-chuan-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LongestNotRepetition {

    public static void main(String[] args) {
        String s = " ";
        int i = new LongestNotRepetition().lengthOfLongestSubstring(s);
        System.out.println(i);
    }

    public int lengthOfLongestSubstring(String s) {
        return dynamic(s);
    }

    public int normal(String s) {
        if (s == null) return 0;
        Map<Character, Integer> map = new HashMap<>();
        int i = -1, res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (map.containsKey(s.charAt(j))) i = Math.max(map.get(s.charAt(j)), i);
            map.put(s.charAt(j), j);
            res = Math.max(res, j - i);
        }
        return res;
    }

    public int dynamic(String s) {
        if (s == null) return 0;
        Map<Character, Integer> map = new HashMap<>();
        int p = 0, ret = 0;
        for (int i = 0; i < s.length(); i++) {
            Integer index = map.getOrDefault(s.charAt(i), -1);
            map.put(s.charAt(i), i);
            p = p < i - index ? p + 1 : i - index;
            ret = Math.max(p, ret);
        }
        return ret;
    }


}
