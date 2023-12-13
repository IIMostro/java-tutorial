package org.ilmostro.basic.algorithm.leetcode.primary_II;

import org.ilmostro.basic.annotation.Attention;
import org.ilmostro.basic.annotation.Middle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 *  [1,2,1],
 *  [2,1,1]]
 * 示例 2：
 *
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 8
 * -10 <= nums[i] <= 10
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */

@Middle
@Attention(algorithm = Attention.Algorithm.BACKTRACK, value = "这是一个比较经典的全排列问题需要重点关注")
public class Permutations_II {

    public static void main(String[] args) {
        int[] nums = {1,1,2};
        List<List<Integer>> lists = new Permutations_II().permuteUnique(nums);
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        visited = new boolean[nums.length];
        Arrays.sort(nums);
        dfs(nums, new ArrayList<>(), 0);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();
    boolean[] visited;
    void dfs(int[] nums, List<Integer> curr, int start){
        if(start == nums.length) {
            result.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if(visited[i]) continue;
            if(i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) continue;
            curr.add(nums[i]);
            visited[i] = true;
            dfs(nums, curr, start + 1);
            visited[i] = false;
            curr.remove(start);
        }
    }
}
