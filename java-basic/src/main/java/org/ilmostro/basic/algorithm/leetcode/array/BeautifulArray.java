package org.ilmostro.basic.algorithm.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 932. 漂亮数组
 * 对于某些固定的 N，如果数组 A 是整数 1, 2, ..., N 组成的排列，使得：
 * <p>
 * 对于每个 i < j，都不存在 k 满足 i < k < j 使得 A[k] * 2 = A[i] + A[j]。
 * <p>
 * 那么数组 A 是漂亮数组。
 * <p>
 * <p>
 * <p>
 * 给定 N，返回任意漂亮数组 A（保证存在一个）。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：4
 * 输出：[2,1,4,3]
 * 示例 2：
 * <p>
 * 输入：5
 * 输出：[3,1,2,5,4]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= N <= 1000
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/beautiful-array/}
 */
public class BeautifulArray {

    Map<Integer, int[]> memo;

    public int[] beautifulArray(int N) {
        memo = new HashMap<>();
        memo.put(1, new int[]{1});
        return f(N);
    }

    private int[] f(int N) {
        if (!memo.containsKey(N)) {
            int index = 0;
            int[] res = new int[N];
            for (int x : f((N + 1) / 2)) {
                res[index++] = 2 * x - 1;
            }
            for (int x : f(N / 2)) {
                res[index++] = 2 * x;
            }
            memo.put(N, res);
        }
        return memo.get(N);
    }

}
