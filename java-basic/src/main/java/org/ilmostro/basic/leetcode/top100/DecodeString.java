package org.ilmostro.basic.leetcode.top100;

import org.ilmostro.basic.annotation.Attention;
import org.ilmostro.basic.annotation.Middle;

import java.util.Stack;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * <p>
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * <p>
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * <p>
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "3[a]2[bc]"
 * 输出："aaabcbc"
 * 示例 2：
 * <p>
 * 输入：s = "3[a2[c]]"
 * 输出："accaccacc"
 * 示例 3：
 * <p>
 * 输入：s = "2[abc]3[cd]ef"
 * 输出："abcabccdcdcdef"
 * 示例 4：
 * <p>
 * 输入：s = "abc3[cd]xyz"
 * 输出："abccdcdcdxyz"
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/decode-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
@Middle
@Attention(solution = Attention.Solution.STRING, value = "字符串的解码")
public class DecodeString {

    public static void main(String[] args) {
        String s = new DecodeString().decodeString("3[a]2[bc]");
        System.out.println(s);
    }

    public String decodeString(String s) {
        return stack(s);
    }


    public String stack(String s) {
        StringBuilder res = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        int multi = 0;
        Stack<Integer> multiStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (c == '[') {
                multiStack.push(multi);
                resStack.push(res.toString());
                multi = 0;
                res.delete(0, res.length());
            } else if (c == ']') {
                tmp.delete(0, tmp.length());
                int cur_multi = multiStack.pop();
                tmp.append(String.valueOf(res).repeat(Math.max(0, cur_multi)));
                res = res.replace(0, res.length(), resStack.pop() + tmp);
            } else if (Character.isDigit(c)) {
                multi = multi * 10 + Integer.parseInt(c + "");
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    public String normal(String s) {
        StringBuffer ans = new StringBuffer();
        Stack<Integer> multiStack = new Stack<>();
        Stack<StringBuffer> ansStack = new Stack<>();
        int multi = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) multi = multi * 10 + c - '0';
            else if (c == '[') {
                ansStack.add(ans);
                multiStack.add(multi);
                ans = new StringBuffer();
                multi = 0;
            } else if (Character.isAlphabetic(c)) {
                ans.append(c);
            } else {
                StringBuffer ansTmp = ansStack.pop();
                int tmp = multiStack.pop();
                ansTmp.append(String.valueOf(ans).repeat(Math.max(0, tmp)));
                ans = ansTmp;
            }
        }
        return ans.toString();
    }

    public String recursion(String s) {
        return dfs(s, 0)[0];
    }

    public String[] dfs(String s, int i) {
        StringBuilder res = new StringBuilder();
        int multi = 0;
        while (i < s.length()) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
                multi = multi * 10 + Integer.parseInt(String.valueOf(s.charAt(i)));
            else if (s.charAt(i) == '[') {
                String[] tmp = dfs(s, i + 1);
                i = Integer.parseInt(tmp[0]);
                while (multi > 0) {
                    res.append(tmp[1]);
                    multi--;
                }
            } else if (s.charAt(i) == ']')
                return new String[]{String.valueOf(i), res.toString()};
            else
                res.append(s.charAt(i));
            i++;
        }
        return new String[]{res.toString()};
    }
}
