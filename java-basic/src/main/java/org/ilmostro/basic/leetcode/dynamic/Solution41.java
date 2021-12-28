package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * <p>
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 * <p>
 * 进阶：
 * <p>
 * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 * <p>
 * 致谢：
 * <p>
 * 特别感谢 @pbrother 添加此问题并且创建所有测试用例。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "abc", t = "ahbgdc"
 * 输出：true
 * 示例 2：
 * <p>
 * 输入：s = "axc", t = "ahbgdc"
 * 输出：false
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= s.length <= 100
 * 0 <= t.length <= 10^4
 * 两个字符串都只由小写字符组成。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/is-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution41 {

    public static void main(String[] args) {
        String s = "b";
        String t = "abc";
        boolean subsequence = new Solution41().isSubsequence(s, t);
        System.out.println(subsequence);
    }

    public boolean isSubsequence(String s, String t) {
        if (s == null || s.length() <= 0) return true;
        if (t == null || t.length() <= 0) return false;
        for (int i = 0; i < t.length(); i++) {
            int index = 0;
            if (s.charAt(index++) != t.charAt(i)) continue;
            for (int j = i + 1; j < t.length(); j++) {
                if (index == s.length()) {
                    return true;
                }
                if (t.charAt(j) == s.charAt(index)) {
                    index++;
                }
                if (index == s.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean normal(String s, String t){
        if (s == null || s.length() <= 0) return true;
        if (t == null || t.length() <= 0) return false;
        int n = s.length(), m = t.length(), i = 0, j = 0;
        while(i < n && j < m){
            if(s.charAt(i) == t.charAt(j)) i++;
            j++;
        }
        return i == n;
    }


    public boolean dynamic(String s, String t){
        int n = s.length(), m = t.length();
        int[][] f = new int[m + 1][26];
        for (int i = 0; i < 26; i++) {
            f[m][i] = m;
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i) == j + 'a')
                    f[i][j] = i;
                else
                    f[i][j] = f[i + 1][j];
            }
        }
        int add = 0;
        for (int i = 0; i < n; i++) {
            if (f[add][s.charAt(i) - 'a'] == m) {
                return false;
            }
            add = f[add][s.charAt(i) - 'a'] + 1;
        }
        return true;
    }
}
