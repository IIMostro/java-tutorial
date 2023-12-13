package org.ilmostro.basic.algorithm.leetcode.primary;

import java.util.Arrays;

/**
 * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
 * <p>
 * 换句话说，s1 的排列之一是 s2 的 子串 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s1 = "ab" s2 = "eidbaooo"
 * 输出：true
 * 解释：s2 包含 s1 的排列之一 ("ba").
 * 示例 2：
 * <p>
 * 输入：s1= "ab" s2 = "eidboaoo"
 * 输出：false
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= s1.length, s2.length <= 104
 * s1 和 s2 仅包含小写字母
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutation-in-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class PermutationInString {

    public static void main(String[] args) {
        boolean b = new PermutationInString().checkInclusion("ab", "eidboaoo");
        System.out.println(b);
    }

    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s1.length() <= 0) return true;
        if (s2 == null || s2.length() <= 0) return false;
        if (s1.length() > s2.length()) return false;

        int[] arr1 = new int[26];
        int[] arr2 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            arr1[s1.charAt(i) - 'a']++;
            arr2[s2.charAt(i) - 'a']++;
        }
        if(Arrays.equals(arr1, arr2)) return true;
        for (int i = s1.length(); i < s2.length(); i++) {
            arr2[s2.charAt(i - s1.length()) - 'a']--;
            arr2[s2.charAt(i) - 'a'] ++;
            if(Arrays.equals(arr1, arr2)) return true;
        }
        return false;
    }
}
