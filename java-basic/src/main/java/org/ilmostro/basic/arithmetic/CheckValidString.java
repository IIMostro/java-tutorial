package org.ilmostro.basic.arithmetic;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * @author li.bowei
 */
@Slf4j
public class CheckValidString {

    public static void main(String[] args) {
        String content = "(*)";
        boolean b = checkValidString(content);
        log.info("content:{}, check:{}", content, b);
    }

    public static boolean checkValidString(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        Stack<Character> star = new Stack<>();
        for (char data : chars) {
            if (data == '(') {
                stack.push(data);
                continue;
            }
            if (data == ')') {
                if(stack.isEmpty() && star.isEmpty()){
                    return false;
                }
                if(stack.isEmpty()){
                    star.pop();
                    continue;
                }
                stack.pop();
                continue;
            }
            if (data == '*'){
                star.push('*');
            }
        }
        return stack.isEmpty() || (!star.isEmpty());
    }
}
