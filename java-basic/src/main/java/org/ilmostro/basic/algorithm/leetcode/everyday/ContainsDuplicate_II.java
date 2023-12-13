package org.ilmostro.basic.algorithm.leetcode.everyday;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3,1], k = 3
 * 输出：true
 * 示例 2：
 *
 * 输入：nums = [1,0,1,1], k = 1
 * 输出：true
 * 示例 3：
 *
 * 输入：nums = [1,2,3,1,2,3], k = 2
 * 输出：false
 *  
 *
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * 0 <= k <= 105
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/contains-duplicate-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ContainsDuplicate_II {


    public static void main(String[] args) {
        int[] nums = {1,2,3,1,2,3};
        boolean b = new ContainsDuplicate_II().containsNearbyDuplicate(nums, 2);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if(nums == null || nums.length <=0) return false;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(nums[i]) && Math.abs(i - map.get(nums[i])) > k) return true;
            else map.put(nums[i], i);
        }
        return false;
    }
}
