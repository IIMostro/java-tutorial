package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

/**
 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ReverseLeftWords {

    public static void main(String[] args) {
        String lrloseumgh = new ReverseLeftWords().reverseLeftWordsV1("lrloseumgh", 6);
        System.out.println(lrloseumgh);
    }

    /**
     * 暴力解
     *
     * @param s 源字符串
     * @param n 什么时候开始反转
     * @return 反转之后
     */
    public String reverseLeftWords(String s, int n) {
        if (s == null || s.length() <= 0) {
            return s;
        }
        if (n <= 0 || n > s.length()) {
            throw new RuntimeException();
        }
        char[] chars = s.toCharArray();
        char[] ret = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            if(i >= n){
                ret[i -n] = chars[i];
            }else{
                ret[chars.length - n + i] = chars[i];
            }
        }
        return new String(ret);
    }


    /**
     * 取模解
     *
     * @param s 源字符串
     * @param n 什么时候开始反转
     * @return 反转之后
     */
    public String reverseLeftWordsV1(String s, int n) {

        StringBuilder sb = new StringBuilder();
        for (int i = n; i < s.length() + n; i++) {
            sb.append(s.charAt(i % s.length()));
        }
        return sb.toString();
    }
}
