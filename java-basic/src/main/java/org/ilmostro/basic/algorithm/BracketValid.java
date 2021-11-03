package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * @author li.bowei
 */
@Slf4j
public class BracketValid {

    public static void main(String[] args) {
        String bracket = "()";
        boolean valid = new BracketValid().isValid(bracket);
        log.info("{}", valid);
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        char[] chars = s.toCharArray();
        for (char ch : chars) {
            if ('(' == ch || '{' == ch || '[' == ch) {
                stack.push(ch);
            }else{
                if(stack.isEmpty()){
                    return false;
                }
                Character pop = stack.pop();
                if(ch == ')' && pop != '('){
                    return false;
                }else if (ch == '}' && pop !='{'){
                    return false;
                }else if(ch == ']' && pop !='['){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
