package org.ilmostro.basic.algorithm.leetcode.array;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数组中占比超过一半的元素称之为主要元素。给你一个 整数 数组，找出其中的主要元素。若没有，返回 -1 。请设计时间复杂度为 O(N) 、空间复杂度为 O(1) 的解决方案。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：[1,2,5,9,5,9,5,5,5]
 * 输出：5
 * 示例 2：
 * <p>
 * 输入：[3,2]
 * 输出：-1
 * 示例 3：
 * <p>
 * 输入：[2,2,1,1,1,2,2]
 * 输出：2
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-majority-element-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class FindMajorityElement {

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        int i = new FindMajorityElement().majorityElement(nums);
        System.out.println(i);
    }

    public int majorityElement(int[] nums) {
        return pointer(nums);
    }

    public int hash(int[] nums) {
        if (nums == null || nums.length <= 0) return -1;
        Map<Integer, Long> map = Arrays.stream(nums)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Map.Entry<Integer, Long> entry : map.entrySet()) {
            if (entry.getValue() > nums.length / 2) return entry.getKey();
        }
        return -1;
    }

    public int pointer(int[] nums) {
        if (nums == null || nums.length <= 0) return -1;
        int n = nums.length;
        if(n == 1) return nums[0];
        if (n == 2) return nums[0] == nums[1] ? nums[0] : -1;
        Arrays.sort(nums);
        int left = 0, right = n - 1;
        while(right - left > n / 2){
            int mid = left + (right - left) / 2;
            if(nums[left] != nums[mid]) left++;
            if(nums[right] != nums[mid]) right--;
            if(nums[left] == nums[right] && left != right) return nums[left];
        }
        return -1;
    }
}
