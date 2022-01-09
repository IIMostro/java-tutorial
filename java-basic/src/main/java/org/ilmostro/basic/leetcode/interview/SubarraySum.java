package org.ilmostro.basic.leetcode.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li.bowei
 */
public class SubarraySum {

    public static void main(String[] args) {
        int[] nums = {1, -1, 0};
        int i = new SubarraySum().hash(nums, 0);
        System.out.println(i);
    }

    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length <= 0) return 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            if(curr == k){
                count++;
            }
            for (int j = i + 1; j < nums.length; j++) {
                curr += nums[j];
                if(curr == k){
                    count++;
                }
            }
        }
        return count;
    }

    public int hash(int[] nums, int k){
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        int count = 0;
        for (int num : nums) {
            sum += num;
            count += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public int preSum(int[] nums, int k){
        int count = 0, pre = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int num : nums) {
            pre += num;
            if (map.containsKey(pre - k)) count += map.get(pre - k);
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return count;
    }
}
