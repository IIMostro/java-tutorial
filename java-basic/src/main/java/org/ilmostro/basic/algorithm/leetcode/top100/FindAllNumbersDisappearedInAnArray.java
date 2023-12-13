package org.ilmostro.basic.algorithm.leetcode.top100;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [4,3,2,7,8,2,3,1]
 * 输出：[5,6]
 * 示例 2：
 *
 * 输入：nums = [1,1]
 * 输出：[2]
 *  
 *
 * 提示：
 *
 * n == nums.length
 * 1 <= n <= 105
 * 1 <= nums[i] <= n
 * 进阶：你能在不使用额外空间且时间复杂度为 O(n) 的情况下解决这个问题吗? 你可以假定返回的数组不算在额外空间内。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class FindAllNumbersDisappearedInAnArray {

    public static void main(String[] args) {
        int[] nums = {1,1};
        List<Integer> disappearedNumbers = new FindAllNumbersDisappearedInAnArray().findDisappearedNumbers(nums);
        for (Integer disappearedNumber : disappearedNumbers) {
            System.out.println(disappearedNumber);
        }
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        return normal(nums);
    }

    public List<Integer> normal(int[] nums){
        if(nums == null || nums.length <= 0) return Collections.emptyList();
        Set<Integer> set = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= nums.length; i++) {
            if(set.contains(i))continue;
            result.add(i);
        }
        return result;
    }

    public List<Integer> optimize(int[] nums){
        if(nums == null || nums.length <= 0) return Collections.emptyList();
        int n = nums.length;
        for (int num : nums) {
            int x = (num - 1) % n;
            nums[x] += n;
        }
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] <= n) {
                ret.add(i + 1);
            }
        }
        return ret;
    }
}
