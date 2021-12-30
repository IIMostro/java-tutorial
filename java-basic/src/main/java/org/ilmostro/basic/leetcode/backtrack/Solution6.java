package org.ilmostro.basic.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 * <p>
 * 说明：
 * <p>
 * 所有数字都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 * <p>
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 * 示例 2:
 * <p>
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-iii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution6 {

    public static void main(String[] args) {
        List<List<Integer>> lists = new Solution6().combinationSum3(3, 15);
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrackV1(new ArrayList<>(), 1, k, n);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();

    public void backtrack(List<Integer> curr, int start, int k, int target) {
        if (start > 9 || target <= 0 || curr.size() >= k)
            return;
        curr.add(start);
        if (curr.size() == k && target - start == 0) {
            result.add(new ArrayList<>(curr));
        }
        backtrack(curr, start + 1, k, target - start);
        curr.remove(curr.size() - 1);
        backtrack(curr, start + 1, k, target);
    }

    public void backtrackV1(List<Integer> curr, int start, int k, int target) {
        if (target < 0 || curr.size() > k) return;
        if (target == 0 && curr.size() == k) result.add(new ArrayList<>(curr));
        for (int i = start; i < 10; i++) {
            if(curr.contains(i)) continue;
            curr.add(i);
            backtrackV1(curr, i + 1, k , target - i);
            curr.remove(curr.size() - 1);
        }
    }
}
