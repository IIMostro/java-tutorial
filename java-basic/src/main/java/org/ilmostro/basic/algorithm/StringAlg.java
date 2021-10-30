package org.ilmostro.basic.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * @author li.bowei
 */
public class StringAlg {

    public static void main(String[] args) {
        String name = "";
        int i = new StringAlg().lengthOfLongestSubstring(name);
        System.out.println(i);
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int max = 1;
        Set<Character> characters = new HashSet<>();
        for (int i = 0; i < chars.length && chars.length - i > max; i++) {
            for (int j = i; j < chars.length && j -i < max; j++) {
                if (characters.contains(chars[j])) {
                    break;
                }
                if (j - i >= max) {
                    max = j - i + 1;
                }
                characters.add(chars[j]);
            }
            characters.clear();
        }
        return max;
    }
}
