package org.ilmostro.basic.leetcode.pointer;

import org.ilmostro.basic.annotation.SolveError;

/**
 * @author li.bowei
 */
public class Solution1 {

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
