package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 输入一个字符串，打印出该字符串中字符的所有排列。
 *
 *  
 *
 * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
 *
 *  
 *
 * 示例:
 *
 * 输入：s = "abc"
 * 输出：["abc","acb","bac","bca","cab","cba"]
 *  
 *
 * 限制：
 *
 * 1 <= s 的长度 <= 8
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class StringPermutation {

    public String[] permutation(String s) {
        dfs(s.toCharArray(), s.length(), 0);
        return result.toArray(String[]::new);
    }

    Set<String> result = new HashSet<>();
    public void dfs(char[] words, int n, int k){
        if (n == k){
            result.add(new String(words));
            return;
        }
        for (int i = k; i < n; i++) {
            swap(words, i, k);
            dfs(words, n, k + 1);
            swap(words,i ,k);
        }
    }

    public void swap(char[] words, int i, int j){
        char temp = words[i];
        words[i] = words[j];
        words[j] = temp;
    }
}
