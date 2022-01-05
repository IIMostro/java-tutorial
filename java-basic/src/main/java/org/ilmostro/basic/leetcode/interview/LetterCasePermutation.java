package org.ilmostro.basic.leetcode.interview;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
 *
 *  
 *
 * 示例：
 * 输入：S = "a1b2"
 * 输出：["a1b2", "a1B2", "A1b2", "A1B2"]
 *
 * 输入：S = "3z4"
 * 输出：["3z4", "3Z4"]
 *
 * 输入：S = "12345"
 * 输出：["12345"]
 *  
 *
 * 提示：
 *
 * S 的长度不超过12。
 * S 仅由数字和字母组成。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/letter-case-permutation
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LetterCasePermutation {

    public static void main(String[] args) {
        List<String> a1b2 = new LetterCasePermutation().letterCasePermutation("a1b2");
        for (String s : a1b2) {
            System.out.println(s);
        }
    }

    public List<String> letterCasePermutation(String s) {
        dfs(s.toCharArray(), 0);
        return result;
    }

    List<String> result = new ArrayList<>();
    public void dfs(char[] words, int index){
        String curr = new String(words);
        if(index > words.length - 1) {
            if (!result.contains(curr)) result.add(curr);
            return;
        }
        if (!result.contains(curr)) result.add(curr);
        if(words[index] >= 'a' && words[index] <= 'z') words[index] = Character.toUpperCase(words[index]);
        dfs(words, index + 1);
        if(words[index] >= 'A' && words[index] <= 'Z') words[index] = Character.toLowerCase(words[index]);
        dfs(words, index + 1);
    }
}
