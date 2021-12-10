package org.ilmostro.basic.leetcode.pigeonhole;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
 * <p>
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-pattern
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution21 {

    public static void main(String[] args) {
        boolean b = new Solution21().wordPattern("abba", "dog ct cat dog");
        System.out.println(b);
    }

    public boolean wordPattern(String pattern, String s) {
        String[] target = s.split(" ");
        if (pattern.length() != target.length) {
            return false;
        }
        Map<Character, Integer> patternCharacterMap = new HashMap<>();
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            Integer put = patternCharacterMap.put(pattern.charAt(i), i);
            Integer put1 = wordMap.put(target[i], i);
            if(!Objects.equals(put, put1)){
                return false;
            }
        }
        return true;
    }
}
