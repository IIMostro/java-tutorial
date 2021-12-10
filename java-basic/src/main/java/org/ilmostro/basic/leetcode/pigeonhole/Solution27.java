package org.ilmostro.basic.leetcode.pigeonhole;

import java.util.Objects;
import java.util.Stack;

/**
 *
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 * @link {https://leetcode-cn.com/problems/longest-valid-parentheses/}
 * @author li.bowei
 */
public class Solution27 {

    public static void main(String[] args) {
        String theses = "(()()";
        int i = new Solution27().longestValidParentheses(theses);
        System.out.println(i);
    }

    public int longestValidParentheses(String s) {
        if(Objects.isNull(s) || "".equals(s)){
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        int max = 0;
        stack.push(-1);
        for (int i = 0; i < s.toCharArray().length; i++) {
            if(s.charAt(i) == '('){
                stack.push(i);
            }else{
                stack.pop();
                if(stack.isEmpty()){
                    stack.push(i);
                }else{
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }
}
