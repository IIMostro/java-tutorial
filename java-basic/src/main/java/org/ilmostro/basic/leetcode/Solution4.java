package org.ilmostro.basic.leetcode;

/**
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度。
 * <p>
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/length-of-last-word/}
 */
public class Solution4 {


    public static void main(String[] args) {
        String string = "a             moon  ";
        int i = new Solution4().lengthOfLastWord(string);
        System.out.println(i);
    }


    public int lengthOfLastWord(String s) {
        int max = 0;
        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if(chars[i] == ' '){
                continue;
            }
            for (int j = i; j >=0; j--){
                if(chars[j] ==' '){
                    break;
                }
                max++;
            }
            break;
        }
        return max;
    }
}
