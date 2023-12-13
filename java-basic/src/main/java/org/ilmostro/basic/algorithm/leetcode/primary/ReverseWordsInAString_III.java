package org.ilmostro.basic.algorithm.leetcode.primary;

/**
 * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 输入："Let's take LeetCode contest"
 * 输出："s'teL ekat edoCteeL tsetnoc"
 *  
 * <p>
 * 提示：
 * <p>
 * 在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-words-in-a-string-iii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ReverseWordsInAString_III {

    public static void main(String[] args) {
        String message = "Let's take LeetCode contest";
        String s = new ReverseWordsInAString_III().reverseWords(message);
        System.out.println(s);
    }

    public String reverseWords(String s) {
        if (s == null || s.length() <= 0) return s;
        String[] split = s.split(" ");
        for (int i = 0; i < split.length; i++) {
            String s1 = split[i];
            char[] temp = s1.toCharArray();
            for (int j = 0, k = s1.length() - 1; j < k; j++, k --) {
                swap(temp, j, k);
            }
            split[i] = new String(temp);
        }
        return String.join(" ", split);
    }

    public void swap(char[] s, int i, int j) {
        if (i == j) return;
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}
