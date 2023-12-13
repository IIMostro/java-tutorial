package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

/**
 *
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 *
 *  
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/que-shi-de-shu-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MissingNumber {

    public static void main(String[] args) {
        int[] ints = new int[]{0,1,2,3,4,5,6,7,9};
        int i = new MissingNumber().missingNumber(ints);
        System.out.println(i);
    }

    public int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            System.out.println("num: "+ num + ", i: " + i + " = "+(num & i));
            if((nums[i] & i) != nums[i]){
                return i;
            }
        }
        return -1;
    }
}
