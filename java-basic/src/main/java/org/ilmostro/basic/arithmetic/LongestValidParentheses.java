package org.ilmostro.basic.arithmetic;

import java.util.Stack;

/**
 * @author li.bowei
 */
public class LongestValidParentheses {

    public int longestValidParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        int length = 0;
        int setup = 0;
        for (int index = 0; index < chars.length; index++) {
            char data = chars[index];
            if(data == '('){
                stack.push(data);
                setup++;
            }
            if(data == ')' && !stack.isEmpty()){
                stack.pop();
            }
        }
        return length;
    }
}
