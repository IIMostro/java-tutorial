package org.ilmostro.basic.algorithm.leetcode.top100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 * <p>
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 200
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Sum4 {

    public static void main(String[] args) {
        int[] nums = {1,0,-1,0,-2,2};
        List<List<Integer>> lists = new Sum4().fourSum(nums, 0);
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length <= 4) return result;
        Arrays.sort(nums);
        dfs(nums, target, new ArrayList<>(), 0, 0);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();

    public void dfs(int[] nums, int target, List<Integer> curr, int index, int sum){
        if(sum == target && curr.size() == 4){
            result.add(new ArrayList<>(curr));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            if(nums.length - i < 4 - curr.size()) return;
            if(i > index && nums[i] == nums[i - 1]) continue;
            if(i < nums.length -1 && sum + nums[i] + (3 - curr.size()) * nums[i + 1] > target) return;
            if(i < nums.length - 1 && sum + nums[i] + (3 - curr.size()) * nums[nums.length - 1] < target) continue;
            curr.add(nums[i]);
            dfs(nums, target, curr, index + 1, sum + nums[i]);
            curr.remove(curr.size() - 1);
        }
    }
}
