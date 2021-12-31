package org.ilmostro.basic.leetcode.interview;

/**
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [4,1,4,6]
 * 输出：[1,6] 或 [6,1]
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,10,4,1,4,3,3]
 * 输出：[2,10] 或 [10,2]
 *  
 * <p>
 * 限制：
 * <p>
 * 2 <= nums.length <= 10000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SingleNumbers {

    public static void main(String[] args) {
        int[] arr = {1,1,2,2,3,5};
        int[] ints = new SingleNumbers().singleNumbers(arr);
    }

    public int[] singleNumbers(int[] nums) {
        int x = 0, y = 0, n = 0, m = 1;
        for (int num : nums)
            n ^= num;
        while ((n & m) == 0)
            m <<= 1;
        for (int num : nums) {
            if ((num & m) != 0) x ^= num;
            else y ^= num;
        }
        return new int[]{x, y};
    }
}
