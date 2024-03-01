package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 *
 * 数值（按顺序）可以分成以下几个部分：
 *
 * 若干空格
 * 一个 小数 或者 整数
 * （可选）一个 'e' 或 'E' ，后面跟着一个 整数
 * 若干空格
 * 小数（按顺序）可以分成以下几个部分：
 *
 * （可选）一个符号字符（'+' 或 '-'）
 * 下述格式之一：
 * 至少一位数字，后面跟着一个点 '.'
 * 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
 * 一个点 '.' ，后面跟着至少一位数字
 * 整数（按顺序）可以分成以下几个部分：
 *
 * （可选）一个符号字符（'+' 或 '-'）
 * 至少一位数字
 * 部分数值列举如下：
 *
 * ["+100", "5e2", "-123", "3.1416", "-1E-16", "0123"]
 * 部分非数值列举如下：
 *
 * ["12e", "1a3.14", "1.2.3", "+-5", "12e+5.4"]
 *  
 *
 * 示例 1：
 *
 * 输入：s = "0"
 * 输出：true
 * 示例 2：
 *
 * 输入：s = "e"
 * 输出：false
 * 示例 3：
 *
 * 输入：s = "."
 * 输出：false
 * 示例 4：
 *
 * 输入：s = "    .1  "
 * 输出：true
 *  
 *
 * 提示：
 *
 * 1 <= s.length <= 20
 * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，空格 ' ' 或者点 '.' 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class IsNumber {

    public boolean isNumber(String s) {
        Map<Integer, Map<Character, Integer>> states = new HashMap<>();
        states.put(0, new HashMap<>() {{ put(' ', 0); put('s', 1); put('d', 2); put('.', 4); }});
        states.put(1, new HashMap<>() {{ put('d', 2); put('.', 4); }});
        states.put(2, new HashMap<>() {{ put('d', 2); put('.', 3); put('e', 5); put(' ', 8); }});
        states.put(3, new HashMap<>() {{ put('d', 3); put('e', 5); put(' ', 8); }});
        states.put(4, new HashMap<>() {{ put('d', 3); }});
        states.put(5, new HashMap<>() {{ put('s', 6); put('d', 7); }});
        states.put(6, new HashMap<>() {{ put('d', 7); }});
        states.put(7, new HashMap<>() {{ put('d', 7); put(' ', 8); }});
        states.put(8, new HashMap<>() {{ put(' ', 8); }} );
        int p = 0;
        char t;
        for(char c : s.toCharArray()) {
            if(c >= '0' && c <= '9') t = 'd';
            else if(c == '+' || c == '-') t = 's';
            else if(c == 'e' || c == 'E') t = 'e';
            else if(c == '.' || c == ' ') t = c;
            else t = '?';
            if(!states.get(p).containsKey(t)) return false;
            p = states.get(p).get(t);
        }
        return p == 2 || p == 3 || p == 7 || p == 8;
    }
}