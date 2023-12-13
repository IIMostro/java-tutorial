package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.annotation.Middle;

import java.util.Arrays;

/**
 * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的某个变位词。
 * <p>
 * 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入: s1 = "ab" s2 = "eidbaooo"
 * 输出: True
 * 解释: s2 包含 s1 的排列之一 ("ba").
 * 示例 2：
 * <p>
 * 输入: s1= "ab" s2 = "eidboaoo"
 * 输出: False
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= s1.length, s2.length <= 104
 * s1 和 s2 仅包含小写字母
 *  
 * <p>
 * 注意：本题与主站 567 题相同： https://leetcode-cn.com/problems/permutation-in-string/
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/MPnaiL
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
@Middle
public class PermutationInString {

    public static void main(String[] args) {
        boolean b = new PermutationInString().checkInclusion("ab", "eidboaoo");
        System.out.println(b);
    }

    public boolean checkInclusion(String s1, String s2) {
        return pointer(s1, s2);
    }

    public boolean windows(String s1, String s2){
        int n = s1.length(), m = s2.length();
        if (n > m) return false;
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];
        for (int i = 0; i < n; i++) {
            cnt1[s1.charAt(i) - 'a'] ++;
            cnt2[s2.charAt(i) - 'a'] ++;
        }
        if (Arrays.equals(cnt1, cnt2)) return true;
        for (int i = n; i < m; ++i) {
            cnt2[s2.charAt(i) - 'a'] ++;
            cnt2[s2.charAt(i - n) - 'a'] --;
            if (Arrays.equals(cnt1, cnt2)) return true;
        }
        return false;
    }

    public boolean pointer(String s1, String s2){
        int n = s1.length(), m = s2.length();
        if (n > m) return false;
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[s1.charAt(i) - 'a']++;
        }
        int left = 0;
        for (int right = 0; right < m; right++) {
            int x = s2.charAt(right) - 'a';
            cnt[x]++;
            while (cnt[x] > 0) {
                cnt[s2.charAt(left) - 'a']--;
                left++;
            }
            if (right - left + 1 == n) return true;
        }
        return false;
    }

}
