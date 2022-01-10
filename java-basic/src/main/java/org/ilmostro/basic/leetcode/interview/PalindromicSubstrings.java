package org.ilmostro.basic.leetcode.interview;

/**
 *
 * 给定一个字符串 s ，请计算这个字符串中有多少个回文子字符串。
 *
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 * 示例 2：
 *
 * 输入：s = "aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 *  
 *
 * 提示：
 *
 * 1 <= s.length <= 1000
 * s 由小写英文字母组成
 *  
 *
 * 注意：本题与主站 647 题相同：https://leetcode-cn.com/problems/palindromic-substrings/ 
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/a7VOhD
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class PalindromicSubstrings {

    public static void main(String[] args) {
        int abc = new PalindromicSubstrings().countSubstrings("aaa");
        System.out.println(abc);
    }

    public int countSubstrings(String s) {
        if(s == null || s.length() <= 0) return 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j >= 0; j--) {
                if (valid(s, j, i)) count++;
            }
        }
        return count;
    }

    public boolean valid(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
}
