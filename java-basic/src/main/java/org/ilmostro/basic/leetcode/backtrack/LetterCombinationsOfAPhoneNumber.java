package org.ilmostro.basic.leetcode.backtrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 *
 *
 *  
 *
 * 示例 1：
 *
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 示例 2：
 *
 * 输入：digits = ""
 * 输出：[]
 * 示例 3：
 *
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 *  
 *
 * 提示：
 *
 * 0 <= digits.length <= 4
 * digits[i] 是范围 ['2', '9'] 的一个数字。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LetterCombinationsOfAPhoneNumber {

    public static void main(String[] args) {
        List<String> strings = new LetterCombinationsOfAPhoneNumber().letterCombinations("23");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public List<String> letterCombinations(String digits) {
        if(digits == null || digits.length() <= 0) return Collections.emptyList();
        List<String> result = new ArrayList<>();
        backtrack(result, digits, 0, new StringBuilder());
        return result;
    }

    String[] words = new String[]{"","","abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public void backtrack(List<String> result, String digits, int index, StringBuilder sb){
        if(index == digits.length()) result.add(sb.toString());
        else{
            char word = digits.charAt(index);
            String letters = words[word - '0'];
            for (int i = 0; i < letters.length(); i++) {
                sb.append(letters.charAt(i));
                backtrack(result, digits, index+1, sb);
                sb.deleteCharAt(index);
            }
        }
    }
}
