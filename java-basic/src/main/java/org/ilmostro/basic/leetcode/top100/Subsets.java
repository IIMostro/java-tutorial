package org.ilmostro.basic.leetcode.top100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 *
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
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
 * nums 中的所有元素 互不相同
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Subsets {

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        List<List<Integer>> subsets = new Subsets().subsets(nums);
    }

    public List<List<Integer>> subsets(int[] nums) {
        dfs(nums, new ArrayList<>(), 0);
        result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();
    public void dfs(int[] nums, List<Integer> curr, int start){
        if(start > nums.length) return;
        result.add(new ArrayList<>(curr));
        for (int i = start; i < nums.length; i++) {
            curr.add(nums[i]);
            dfs(nums, curr, i + 1);
            curr.remove(curr.size() - 1);
        }
    }
}
