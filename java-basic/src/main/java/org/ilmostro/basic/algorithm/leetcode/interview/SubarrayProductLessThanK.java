package org.ilmostro.basic.algorithm.leetcode.interview;

/**
 *
 * 给定一个正整数数组 nums和整数 k ，请找出该数组内乘积小于 k 的连续的子数组的个数。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: nums = [10,5,2,6], k = 100
 * 输出: 8
 * 解释: 8 个乘积小于 100 的子数组分别为: [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]。
 * 需要注意的是 [10,5,2] 并不是乘积小于100的子数组。
 * 示例 2:
 *
 * 输入: nums = [1,2,3], k = 0
 * 输出: 0
 *  
 *
 * 提示: 
 *
 * 1 <= nums.length <= 3 * 104
 * 1 <= nums[i] <= 1000
 * 0 <= k <= 106
 *  
 *
 * 注意：本题与主站 713 题相同：https://leetcode-cn.com/problems/subarray-product-less-than-k/ 
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ZVAVXX
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SubarrayProductLessThanK {

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        int k = new SubarrayProductLessThanK().numSubarrayProductLessThanK(nums, 0);
        System.out.println(k);
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(nums == null || nums.length <= 0) return 0;
        int ans = 0, sum = 1;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] < k) {
                sum *= nums[i];
                ans++;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if(sum * nums[j] < k){
                    sum *= nums[j];
                    ans++;
                }else{
                    break;
                }
            }
            sum = 1;
        }
        return ans;
    }
}
