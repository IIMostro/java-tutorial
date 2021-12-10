package org.ilmostro.basic.leetcode.pigeonhole;

/**
 * @author li.bowei
 */
public class Solution6 {

    public static void main(String[] args) {
        String code = "loveleetcode";
        int i = firstUniqChar(code);
        System.out.println(i);
    }

    public static int firstUniqChar(String s) {

        int[] words = new int[26];
        char[] chars = s.toCharArray();
        for (char var1 : chars) {
            words[var1 - 'a'] += 1;
        }
        for (int i = 0; i < chars.length; i++) {
            if(words[chars[i] -'a'] == 1){
                return i;
            }
        }
        return -1;
    }
}
