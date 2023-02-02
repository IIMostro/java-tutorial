package org.ilmostro.basic.leetcode.everyday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 */
public class EveryDay {


	/**
	 * 1814. 统计一个数组中好对子的数目
	 * @link <a href="https://leetcode.cn/problems/count-nice-pairs-in-an-array/">...</a>
	 *
	 * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i]) -> nums[i] - rev(nums[i]) == nums[j] - rev(nums[j])
	 * 这样的话就可以利用把nums[i] - rev(nums[i])作为key, 如果value > 2的话就说明有，并且需要需要自由的排列组合
	 *
	 * @param nums 数组
	 * @return 个数
	 */
	public int countNicePairs(int[] nums) {
		if (nums.length <= 1) return 0;

		int result = 0;
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if ((nums[i] + reverse(nums[j])) == (reverse(nums[i]) + nums[j])) result++;
			}
		}
		return result;
	}

	public int countNicePairs_v2(int[] nums){
		final int mod = (int) 1e9 + 7;
		Map<Integer, Integer> cnt = new HashMap<>();
		int ans = 0;
		for (int x : nums) {
			int y = x - reverse(x);
			ans = (ans + cnt.getOrDefault(y, 0)) % mod;
			cnt.merge(y, 1, Integer::sum);
		}
		return ans;
	}


	private int reverse(int num){
		final String str = String.valueOf(num);
		final String reverse = new StringBuffer(str).reverse().toString();
		return Integer.parseInt(reverse);
	}

	/**
	 * 重复的全排列
	 *
	 * @link <a href="https://leetcode.cn/problems/VvJkup/">...</a>
	 * @param nums
	 * @return
	 */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		final List<Integer> temp = Arrays.stream(nums).boxed().collect(Collectors.toList());
		dfs(result, temp, nums.length, 0);
		return result;
	}

	public void dfs(List<List<Integer>> result ,List<Integer> temp, Integer n, Integer k){
		if (Objects.equals(n, k)){
			result.add(new ArrayList<>(temp));
			return;
		}
		for (int i = k; i < n; i++) {
			Collections.swap(temp, i, k);
			dfs(result, temp, n, k+ 1);
			Collections.swap(temp, i, k);
		}
	}

}
