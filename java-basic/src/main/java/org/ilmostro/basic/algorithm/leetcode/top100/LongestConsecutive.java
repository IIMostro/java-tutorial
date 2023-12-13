package org.ilmostro.basic.algorithm.leetcode.top100;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * <p>
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 * <p>
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-consecutive-sequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LongestConsecutive {

    public static void main(String[] args) {
        int[] nums = {0,3,7,2,5,8,4,6,0,1};
        int i = new LongestConsecutive().longestConsecutive(nums);
        System.out.println(i);
    }

    public int longestConsecutive(int[] nums) {
        if(nums == null || nums.length <= 0) return 0;
        Arrays.sort(nums);
        int ans = 0, size = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (j == 0 || nums[j] == nums[j - 1]) continue;
                if (nums[j] - nums[j - 1] == 1) {
                    size++;
                    ans = Math.max(ans, size);
                } else break;
            }
            if(ans > nums.length - i){
                break;
            }
            size = 0;

        }
        return ans + 1;
    }

    public int normal(int[] nums){
        if(nums == null || nums.length <= 0) return 0;
        Set<Integer> set = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        int ans = 0;

        for (Integer integer : set) {
            int curr = integer;
            if(!set.contains(curr - 1)){
                while(set.contains(curr+1)){
                    curr++;
                }
            }
            ans = Math.max(ans, curr - integer + 1);
        }
        return ans;
    }
}
