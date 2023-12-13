package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

/**
 *
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 *  
 *
 * 示例：
 *
 * s = "leetcode"
 * 返回 0
 *
 * s = "loveleetcode"
 * 返回 2
 *  
 *
 * 提示：你可以假定该字符串只包含小写字母。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/first-unique-character-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class FirstUniqueCharacterInAString {

    public static void main(String[] args) {
        String code = "loveleetcode";
        int i = firstUniqChar(code);
        System.out.println(i);
    }

    public static int firstUniqChar(String s) {

        int[] words = new int[26];
        char[] chars = s.toCharArray();
        for (char var1 : chars) {
            words[var1 - 'a'] += 1;
        }
        for (int i = 0; i < chars.length; i++) {
            if(words[chars[i] -'a'] == 1){
                return i;
            }
        }
        return -1;
    }
}
