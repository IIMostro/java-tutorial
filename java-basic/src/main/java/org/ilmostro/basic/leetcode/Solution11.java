package org.ilmostro.basic.leetcode;

/**
 *
 * 句子 是一个单词列表，列表中的单词之间用单个空格隔开，且不存在前导或尾随空格。每个单词仅由大小写英文字母组成（不含标点符号）。
 *
 * 例如，"Hello World"、"HELLO" 和 "hello world hello world" 都是句子。
 * 给你一个句子 s​​​​​​ 和一个整数 k​​​​​​ ，请你将 s​​ 截断 ​，​​​使截断后的句子仅含 前 k​​​​​​ 个单词。返回 截断 s​​​​​​ 后得到的句子。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/truncate-sentence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @link {https://leetcode-cn.com/problems/truncate-sentence/}
 * @author li.bowei
 */
public class Solution11 {

    public static void main(String[] args) {
        String message = "Hello how are you Contestant";
        String s = new Solution11().truncateSentence1(message, 4);
        System.out.println(s);
    }

    public String truncateSentence(String s, int k) {
        String[] split = s.split(" ");
        String[] result = new String[k];
        if (k >= 0) System.arraycopy(split, 0, result, 0, k);
        return String.join(" ", result);
    }

    public String truncateSentence1(String s, int k) {
        String[] result = new String[k];
        if (k >= 0) System.arraycopy(s.split(" "), 0, result, 0, k);
        return String.join(" ", result);
    }
}
