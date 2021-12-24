package org.ilmostro.basic.leetcode.pigeonhole;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 有重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合。
 * <p>
 * 示例1:
 * <p>
 * 输入：S = "qqe"
 * 输出：["eqq","qeq","qqe"]
 * 示例2:
 * <p>
 * 输入：S = "ab"
 * 输出：["ab", "ba"]
 * 提示:
 * <p>
 * 字符都是英文字母。
 * 字符串长度在[1, 9]之间。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutation-ii-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution46 {

    public static void main(String[] args) {
        String message = "abc";
        String[] permutation = new Solution46().permutation(message);
        for (String s : permutation) {
            System.out.println(s);
        }
    }

    LinkedList<String> list = new LinkedList<>();
    public String[] permutation(String S) {
        dfs(S.toCharArray(), 0);
        return list.toArray(new String[0]);
    }

    public void dfs(char[] c, int k) {
        if (k == c.length) {
            list.add(new String(c));
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i = k; i < c.length; i++) {
            if (!set.contains(c[i])) {
                set.add(c[i]);
                swap(c, i, k);
                dfs(c, k + 1);
                swap(c, i, k);
            }
        }
    }

    public void swap(char[] c, int x, int y) {
        char temp = c[x];
        c[x] = c[y];
        c[y] = temp;
    }
}
