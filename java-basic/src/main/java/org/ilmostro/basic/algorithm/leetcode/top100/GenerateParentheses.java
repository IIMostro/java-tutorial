package org.ilmostro.basic.algorithm.leetcode.top100;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 *
 * 输入：n = 1
 * 输出：["()"]
 *  
 *
 * 提示：
 *
 * 1 <= n <= 8
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class GenerateParentheses {

    public static void main(String[] args) {
        List<String> strings = new GenerateParentheses().generateParenthesis(3);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public List<String> generateParenthesis(int n) {
        backtrack(0, 0, n);
        return result;
    }

    List<String> result = new ArrayList<>();
    StringBuffer sb = new StringBuffer();
    public void backtrack(int open, int close, int n){
        if(sb.length() == n * 2){
            result.add(sb.toString());
            return;
        }
        if (open < n){
            sb.append("(");
            backtrack(open+1, close, n);
            sb.deleteCharAt(sb.length() - 1);
        }
        if(close < open){
            sb.append(")");
            backtrack(open, close+1, n);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
