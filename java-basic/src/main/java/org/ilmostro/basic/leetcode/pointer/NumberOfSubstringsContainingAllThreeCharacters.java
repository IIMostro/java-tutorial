package org.ilmostro.basic.leetcode.pointer;

/**
 * 给你一个字符串 s ，它只包含三种字符 a, b 和 c 。
 * <p>
 * 请你返回 a，b 和 c 都 至少 出现过一次的子字符串数目。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "abcabc"
 * 输出：10
 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" 和 "abc" (相同字符串算多次)。
 * 示例 2：
 * <p>
 * 输入：s = "aaacb"
 * 输出：3
 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "aaacb", "aacb" 和 "acb" 。
 * 示例 3：
 * <p>
 * 输入：s = "abc"
 * 输出：1
 *  
 * <p>
 * 提示：
 * <p>
 * 3 <= s.length <= 5 x 10^4
 * s 只包含字符 a，b 和 c 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-substrings-containing-all-three-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class NumberOfSubstringsContainingAllThreeCharacters {

    public static void main(String[] args) {
        int abcabc = new NumberOfSubstringsContainingAllThreeCharacters().numberOfSubstrings("aaabc");
        System.out.println(abcabc);
    }

    public int numberOfSubstrings(String s) {
        if (s == null || s.length() < 3) return 0;
        int ans = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            while(abc(s, start, i)){
                ans += s.length() - i;
                start++;
            }
        }
        return ans;
    }

    public static boolean abc(String s, int l, int r) {
        String substring = s.substring(l, r + 1);
        return substring.contains("a") && substring.contains("b") && substring.contains("c");
    }
}
