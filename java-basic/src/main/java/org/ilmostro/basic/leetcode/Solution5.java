package org.ilmostro.basic.leetcode;

/**
 * 实现 strStr() 函数。
 * <p>
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回  -1 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/implement-strstr
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/implement-strstr/}
 */
public class Solution5 {

    public static void main(String[] args) {
        int i = Solution5.strStr("mississippi", "issipi");
        System.out.println(i);
    }

    public static int strStr(String haystack, String needle) {
        if (needle == null || "".equals(needle)) {
            return 0;
        }
        if (haystack.length() < needle.length()) {
            return -1;
        }
        char[] chars = haystack.toCharArray();
        char[] patternChars = needle.toCharArray();
        int index = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != patternChars[0]) {
                continue;
            }
            for (int j = 0; j < patternChars.length; j++) {
                if ((i + j) < chars.length && patternChars[j] == chars[i + j]) {
                    index = i;
                } else {
                    index = -1;
                    break;
                }
            }
            if (index != -1) {
                return index;
            }
        }
        return index;
    }
}
