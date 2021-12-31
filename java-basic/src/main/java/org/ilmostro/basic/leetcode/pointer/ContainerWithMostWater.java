package org.ilmostro.basic.leetcode.pointer;

import org.ilmostro.basic.annotation.SolveError;

/**
 *
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器。
 *
 *  
 *
 * 示例 1：
 *
 *
 *
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 示例 2：
 *
 * 输入：height = [1,1]
 * 输出：1
 * 示例 3：
 *
 * 输入：height = [4,3,2,1,4]
 * 输出：16
 * 示例 4：
 *
 * 输入：height = [1,2,1]
 * 输出：2
 *  
 *
 * 提示：
 *
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ContainerWithMostWater {

    public int maxArea(int[] height) {
        return optimize(height);
    }

    @SolveError("超时")
    public int normal(int[] height){
        if (height == null || height.length < 2) return 0;
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = height.length - 1; j > i; j--) {
                max = Math.max((j - i) * Math.min(height[i], height[j]), max);
            }
        }
        return max;
    }

    public int optimize(int[] height){
        if (height == null || height.length < 2) return 0;
        int left = 0, right = height.length - 1, max = 0;
        while(left < right){
            max = Math.max((right - left) * Math.min(height[left], height[right]), max);
            if(height[left] < height[right]) left++;
            else right--;
        }
        return max;
    }
}
