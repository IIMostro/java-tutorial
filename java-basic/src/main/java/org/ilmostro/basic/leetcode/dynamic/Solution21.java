package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定一个32位整数 num，你可以将一个数位从0变为1。请编写一个程序，找出你能够获得的最长的一串1的长度。
 * <p>
 * 示例 1：
 * <p>
 * 输入: num = 1775(110111011112)
 * 输出: 8
 * 示例 2：
 * <p>
 * 输入: num = 7(01112)
 * 输出: 4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-bits-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution21 {

    public static void main(String[] args) {
        int number = 2147483647;
        System.out.println(Integer.toBinaryString(number));
        int i = new Solution21().reverseBits(0);
        System.out.println(i);
    }

    public int reverseBits(int num) {
//        if(num == 0) return 1;
        return dynamic(num);
    }

    public int dynamic(int num) {
        int max = 0;
        int reverse = 0;
        int current = 0;
        for(int i=0;i<32;i++){
            if((num&1)==1){
                current++;
                reverse++;
            }else{
                reverse = current+1;
                current = 0;
            }
            if(reverse>max) max = reverse;
            num >>= 1;
        }
        return max;
    }

}
