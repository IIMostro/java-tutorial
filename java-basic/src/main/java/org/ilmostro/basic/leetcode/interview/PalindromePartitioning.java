package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.annotation.Attention;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
 * <p>
 * 回文串 是正着读和反着读都一样的字符串。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "aab"
 * 输出：[["a","a","b"],["aa","b"]]
 * 示例 2：
 * <p>
 * 输入：s = "a"
 * 输出：[["a"]]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 16
 * s 仅由小写英文字母组成
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-partitioning
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
@Attention(algorithm = {Attention.Algorithm.MEMORY_SEARCH, Attention.Algorithm.BACKTRACK},
        value = "这个题目包含了回溯算法和记忆搜索")
public class PalindromePartitioning {

    public static void main(String[] args) {

        List<List<String>> google = new PalindromePartitioning().partition("google");
        System.out.println(google);
    }

    public List<List<String>> partition(String s) {
        memory = new int[s.length()][s.length()];
        dfs(s, new ArrayList<>(), 0);
        return ans;
    }

    List<List<String>> ans = new ArrayList<>();
    int[][] memory;

    void dfs(String s, List<String> curr, int index) {
        if (s.length() <= index) {
            ans.add(new ArrayList<>(curr));
            return;
        }
        for (int i = index; i < s.length(); i++) {
            if (!palindrome(s, index, i)) continue;
            curr.add(s.substring(index, i + 1));
            dfs(s, curr, i + 1);
            curr.remove(curr.size() - 1);
        }
    }

    boolean palindrome(String word, int l, int r) {
        if (memory[l][r] != 0) return memory[l][r] == 1;
        if (l >= r) memory[l][r] = 1;
        else if (word.charAt(l) == word.charAt(r)) memory[l][r] = palindrome(word, l + 1, r - 1) ? 1 : -1;
        else memory[l][r] = -1;
        return memory[l][r] == 1;
    }
}
