package org.ilmostro.basic.algorithm.leetcode.primary;

/**
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
 * <p>
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = ["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 * 示例 2：
 * <p>
 * 输入：s = ["H","a","n","n","a","h"]
 * 输出：["h","a","n","n","a","H"]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 105
 * s[i] 都是 ASCII 码表中的可打印字符
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ReverseString {

    public static void main(String[] args) {
        String message = "hello";
        char[] chars = message.toCharArray();
        new ReverseString().normal(chars);
        for (char aChar : chars) {
            System.out.println(aChar);
        }
    }

    public void reverseString(char[] s) {
        if (s == null || s.length <= 0) return;
        if (s.length % 2 == 0) {
            int mid = s.length / 2;
            int curr = 0;
            while (curr < mid) {
                swap(s, mid - curr - 1, mid + curr);
                curr++;
            }
        } else {
            int mid = s.length / 2;
            int curr = 1;
            while (curr <= mid) {
                swap(s, mid + curr, mid - curr);
                curr++;
            }
        }
    }

    public void normal(char[] s) {
        int n = s.length;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            swap(s, i, j);
        }
    }

    public void swap(char[] s, int i, int j) {
        if (i == j) return;
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}
