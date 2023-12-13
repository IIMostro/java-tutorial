package org.ilmostro.basic.algorithm.leetcode.interview;

/**
 * URL化。编写一种方法，将字符串中的空格全部替换为%20。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。（注：用Java实现的话，请使用字符数组实现，以便直接在数组上操作。）
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入："Mr John Smith    ", 13
 * 输出："Mr%20John%20Smith"
 * 示例 2：
 * <p>
 * 输入："               ", 5
 * 输出："%20%20%20%20%20"
 *  
 * <p>
 * 提示：
 * <p>
 * 字符串长度在 [0, 500000] 范围内。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/string-to-url-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class StringToUrl {

    public static void main(String[] args) {
        String message = "Mr John Smith    ";
        String s = new StringToUrl().replaceSpaces(message, 13);
        System.out.println(s);
    }

    public String replaceSpaces(String S, int length) {
        char[] chars = new char[length * 3 + 1];
        int size = 0;
        for (int i = 0; i < length && i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == ' ') {
                chars[size++] = '%';
                chars[size++] = '2';
                chars[size++] = '0';
            } else {
                chars[size++] = c;
            }
        }
        int last = 0;
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] == '\u0000'){
                last = i;
                break;
            }
        }
        char[] chars1 = new char[last];
        System.arraycopy(chars, 0, chars1, 0, last);
        return new String(chars1);
    }
}
