package org.ilmostro.basic.leetcode.pigeonhole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 示例 2：
 *
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * 示例 3：
 *
 * 输入：nums = [1]
 * 输出：[[1]]
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Permutations {

    public static void main(String[] args) {
        int[] arr = {1,2,3};
        List<List<Integer>> permute = new Permutations().permute(arr);
        for (List<Integer> integers : permute) {
            for (Integer integer : integers) {
                System.out.print(integer + ",");
            }
            System.out.println();
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        dfs(list, nums.length, 0);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();
    public void dfs(List<Integer> list, int n, int k){
        if(n == k){
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = k; i < n; i++) {
            Collections.swap(list, i, k);
            dfs(list, n, k +1);
            Collections.swap(list, i, k);
        }
    }
}
