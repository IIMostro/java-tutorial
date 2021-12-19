package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.annotation.SolveError;

/**
 * 搜索旋转数组。给定一个排序后的数组，包含n个整数，但这个数组已被旋转过很多次了，次数不详。请编写代码找出数组中的某个元素，假设数组元素原先是按升序排列的。若有多个相同元素，返回索引值最小的一个。
 * <p>
 * 示例1:
 * <p>
 * 输入: arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 5
 * 输出: 8（元素5在该数组中的索引）
 * 示例2:
 * <p>
 * 输入：arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 11
 * 输出：-1 （没有找到）
 * 提示:
 * <p>
 * arr 长度范围在[1, 1000000]之间
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-rotate-array-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution26 {

    public static void main(String[] args) {

        int[] nums = {1, 1, 1, 1, 2, 1, 1, 1};
        int search = new Solution26().search(nums, 2);
        System.out.println(search);
    }

    public int search(int[] arr, int target) {
        return binarySearch(arr, 0, arr.length - 1, target);
    }

    @SolveError
    public int binarySearch(int[] arr, int l, int r, int target) {
        return 0;
    }
}
