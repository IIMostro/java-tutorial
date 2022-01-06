package org.ilmostro.basic.leetcode.top100;

import org.ilmostro.basic.annotation.TimeOut;

/**
 *
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 *
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 *  
 *
 * 示例 1:
 *
 *
 *
 * 输入：heights = [2,1,5,6,2,3]
 * 输出：10
 * 解释：最大的矩形为图中红色区域，面积为 10
 * 示例 2：
 *
 *
 *
 * 输入： heights = [2,4]
 * 输出： 4
 *  
 *
 * 提示：
 *
 * 1 <= heights.length <=105
 * 0 <= heights[i] <= 104
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-rectangle-in-histogram
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LargestRectangleInHistogram {
    public static void main(String[] args) {
        int[] nums = {2,1,5,6,2,3};
        int i = new LargestRectangleInHistogram().largestRectangleArea(nums);
        System.out.println(i);
    }

    public int largestRectangleArea(int[] heights) {
        return dynamic(heights);
    }

    @TimeOut
    public int pointer(int[] heights) {
        int sum = 0;
        for (int i = 0; i < heights.length; i++) {
            int left = i, right = i;
            while (left >= 0) {
                if (heights[left] < heights[i]) break;
                left--;
            }
            while (right < heights.length) {
                if (heights[right] < heights[i]) break;
                right++;
            }
            int width = right - left - 1;
            int height = heights[i];
            sum = Math.max(sum, width * height);
        }
        return sum;
    }

    public int dynamic(int[] heights) {
        if (heights == null || heights.length <= 0) return 0;
        int n = heights.length;
        //向左边看找到第一个比自己短的index
        int[] left = new int[n];
        //向右边看找到第一个比自己短的index
        int[] right = new int[n];

        left[0] = -1;
        for (int i = 1; i < n; i++) {
            int k = i - 1;
            while (k >= 0 && heights[k] >= heights[i]) {
                k = left[k];
            }
            left[i] = k;
        }

        right[n - 1] = n;
        for (int i = n - 2; i >= 0; i--) {
            int k = i + 1;
            while (k < n && heights[k] >= heights[i]) {
                k = right[k];
            }
            right[i] = k;
        }

        // left = [-1, -1, 1, 2, 1, 4]
        // right = [1, 6, 4, 4, 6, 6]
        int square = 0;
        for (int i = 0; i < n; i++) {
            square = Math.max((right[i] - left[i] - 1) * heights[i], square);
        }
        return square;
    }
}