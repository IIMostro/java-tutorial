package org.ilmostro.basic.leetcode.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * <p>
 * 你可以按 任何顺序 返回答案。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 4, k = 2
 * 输出：
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 * 示例 2：
 * <p>
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= n <= 20
 * 1 <= k <= n
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combinations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Combinations {

    public static void main(String[] args) {
        List<List<Integer>> combine = new Combinations().combine(4, 2);
    }

    public List<List<Integer>> combine(int n, int k) {
        int[] arr = IntStream.range(1, n + 1).toArray();
        dfs(arr, new ArrayList<>(), k, 0);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();

    public void dfs(int[] arr, List<Integer> number, int k, int l) {
        if(number.size() == k){
            result.add(new ArrayList<>(number));
            return;
        }
        for (int i = l; i < arr.length; i++) {
            number.add(arr[i]);
            dfs(arr, number, k, i + 1);
            number.remove(number.size() - 1);
        }
    }

    public void dfsV1(int arr, int k, int l){

    }
}
