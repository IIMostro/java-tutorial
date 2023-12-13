package org.ilmostro.basic.algorithm.leetcode.pigeonhole;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 *
 * @link {https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof/}
 * @author li.bowei
 */
public class ReplaceBlank {

    public static void main(String[] args) {
        String s = new ReplaceBlank().replaceSpaceV1("We are happy.");
        System.out.println(s);
    }

    public String replaceSpace(String s) {
        if(s == null || s.length() <= 0){
            return s;
        }
        char[] chars = s.toCharArray();
        List<Character> result = new ArrayList<>();
        for (char aChar : chars) {
            if (aChar == ' ') {
                result.add('%');
                result.add('2');
                result.add('0');
            } else {
                result.add(aChar);
            }
        }
        char[] ret = new char[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ret[i] = result.get(i);
        }

        return new String(ret);
    }

    public String replaceSpaceV1(String s){
        if(s == null || s.length() <= 0){
            return s;
        }
        char[] chars = new char[s.length() * 3];
        int size = 0;
        for (char c : s.toCharArray()) {
            if(c == ' '){
                chars[size++] = '%';
                chars[size++] = '2';
                chars[size++] = '0';
            }else{
                chars[size++] = c;
            }
        }
        return new String(chars, 0, size);
    }
}
