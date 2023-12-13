package org.ilmostro.basic.algorithm.leetcode.primary_II;

import java.util.*;

/**
 *
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
 *
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 * 示例 2：
 *
 * 输入：nums = [0]
 * 输出：[[],[0]]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Subsets_II {

    public static void main(String[] args) {
        int[] nums = {1,2,2};
        List<List<Integer>> lists = new Subsets_II().subsetsWithDup(nums);
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        dfs(nums, new LinkedList<>(), 0);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();
    public void dfs(int[] nums, Deque<Integer> curr, int start){
        if(start == nums.length){
            result.add(new ArrayList<>(curr));
            return;
        }
        curr.add(nums[start]);
        dfs(nums, curr, start+1);
        curr.removeLast();
        while(start < nums.length - 1 && nums[start] == nums[start + 1]){
            start++;
        }
        dfs(nums, curr, start +1);
    }
}
