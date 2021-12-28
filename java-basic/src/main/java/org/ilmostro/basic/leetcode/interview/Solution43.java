package org.ilmostro.basic.leetcode.interview;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 *
 *  
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: [1, 2, 3, 2, 2, 2, 5, 4, 2]
 * 输出: 2
 *  
 *
 * 限制：
 *
 * 1 <= 数组长度 <= 50000
 *
 *  
 *
 * 注意：本题与主站 169 题相同：https://leetcode-cn.com/problems/majority-element/
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-chu-xian-ci-shu-chao-guo-yi-ban-de-shu-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution43 {

    public static void main(String[] args) {
        int[] arr = {1,2,3,2,2,2,5,4,2};
        int i = new Solution43().majorityElement(arr);
        System.out.println(i);
    }

    public int majorityElement(int[] nums) {
        if(nums == null || nums.length <= 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.getOrDefault(num, 0) >= nums.length / 2) {
                return num;
            }
            map.merge(num, 1, Integer::sum);
        }
        return nums[0];
    }
}
