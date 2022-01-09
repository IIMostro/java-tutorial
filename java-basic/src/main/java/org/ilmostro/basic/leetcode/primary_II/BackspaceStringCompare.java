package org.ilmostro.basic.leetcode.primary_II;

import java.util.Stack;

/**
 *
 * 给定 s 和 t 两个字符串，当它们分别被输入到空白的文本编辑器后，请你判断二者是否相等。# 代表退格字符。
 *
 * 如果相等，返回 true ；否则，返回 false 。
 *
 * 注意：如果对空文本输入退格字符，文本继续为空。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "ab#c", t = "ad#c"
 * 输出：true
 * 解释：S 和 T 都会变成 “ac”。
 * 示例 2：
 *
 * 输入：s = "ab##", t = "c#d#"
 * 输出：true
 * 解释：s 和 t 都会变成 “”。
 * 示例 3：
 *
 * 输入：s = "a##c", t = "#a#c"
 * 输出：true
 * 解释：s 和 t 都会变成 “c”。
 * 示例 4：
 *
 * 输入：s = "a#c", t = "b"
 * 输出：false
 * 解释：s 会变成 “c”，但 t 仍然是 “b”。
 *  
 *
 * 提示：
 *
 * 1 <= s.length, t.length <= 200
 * s 和 t 只含有小写字母以及字符 '#'
 *  
 *
 * 进阶：
 *
 * 你可以用 O(N) 的时间复杂度和 O(1) 的空间复杂度解决该问题吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/backspace-string-compare
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class BackspaceStringCompare {

    public static void main(String[] args) {
        boolean b = new BackspaceStringCompare().backspaceCompare("ab#c", "ad#c");
        System.out.println(b);
    }

    public boolean backspaceCompare(String s, String t) {
        return stack(s, t);
    }

    public boolean stack(String s, String t){
        if(s == null || t == null || s.equals(t)) return true;
        Stack<Character> st1 = new Stack<>();
        Stack<Character> st2 = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '#'){
                if(!st1.isEmpty()) st1.pop();
            }else{
                st1.push(s.charAt(i));
            }
        }

        for (int i = 0; i < t.length(); i++) {
            if (t.charAt(i) == '#'){
                if(!st2.isEmpty()) st2.pop();
            }else{
                st2.push(t.charAt(i));
            }
        }

        StringBuilder sb1 = new StringBuilder();
        while(!st1.isEmpty()){
            sb1.append(st1.pop());
        }
        StringBuilder sb2 = new StringBuilder();
        while(!st2.isEmpty()){
            sb2.append(st2.pop());
        }
        return sb1.toString().equals(sb2.toString());
    }

    public boolean optimizeStack(String s, String t){
        if(s == null || t == null || s.equals(t)) return true;
        return build(s).equals(build(t));
    }

    public String build(String str){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != '#') sb.append(str.charAt(i));
            else{
                if(sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

}
