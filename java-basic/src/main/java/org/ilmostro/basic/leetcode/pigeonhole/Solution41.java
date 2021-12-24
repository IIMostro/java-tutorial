package org.ilmostro.basic.leetcode.pigeonhole;

/**
 *
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 *
 * 给你一个可能存在 重复 元素值的数组 numbers ，它原来是一个升序排列的数组，并按上述情形进行了一次旋转。请返回旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一次旋转，该数组的最小值为1。  
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution41 {

    public int minArray(int[] numbers) {
        if(numbers == null || numbers.length <= 0){
            return -1;
        }
        if(numbers.length == 1){
            return numbers[0];
        }

        int l = 0;
        int r = numbers.length - 1;
        while(l < r){
            int mid = l + (r -l) / 2;
            if(numbers[mid] > numbers[r]){
                l = mid + 1;
            }else if (numbers[mid] < numbers[r]){
                r = mid - 1;
            }else{
                r -= 1;
            }
        }
        return numbers[l];
    }
}
