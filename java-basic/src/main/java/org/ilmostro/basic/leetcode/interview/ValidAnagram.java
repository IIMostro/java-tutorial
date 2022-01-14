package org.ilmostro.basic.leetcode.interview;

import java.util.Arrays;

/**
 *
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 *
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 示例 2:
 *
 * 输入: s = "rat", t = "car"
 * 输出: false
 *  
 *
 * 提示:
 *
 * 1 <= s.length, t.length <= 5 * 104
 * s 和 t 仅包含小写字母
 *  
 *
 * 进阶: 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-anagram
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ValidAnagram {

    public static void main(String[] args) {
        boolean anagram = new ValidAnagram().isAnagram("a", "a");
        System.out.println(anagram);
    }

    public boolean isAnagram(String s, String t) {
        return sort(s, t);
    }

    public boolean hash(String s, String t){
        if(s.equals(t)) return true;
        if(s.length() != t.length()) return false;
        int[] s1 = new int[26];
        int[] s2 = new int[26];
        for (int i = 0; i < s.length(); i++) {
            s1[s.charAt(i) - 'a']++;
            s2[t.charAt(i) - 'a']++;
        }
        return Arrays.equals(s1, s2);
    }

    public boolean sort(String s, String t){
        char[] chars = s.toCharArray();
        char[] chars1 = t.toCharArray();
        Arrays.sort(chars);
        Arrays.sort(chars1);
        return Arrays.equals(chars, chars1);
    }
}
