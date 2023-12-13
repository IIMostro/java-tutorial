package org.ilmostro.basic.algorithm.leetcode.interview;

/**
 * 给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，其中 B[i] 的值是数组 A 中除了下标 i 以外的元素的积, 即 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
 * <p>
 *  
 * <p>
 * 示例:
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: [120,60,40,30,24]
 *  
 * <p>
 * 提示：
 * <p>
 * 所有元素乘积之和不会溢出 32 位整数
 * a.length <= 100000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/gou-jian-cheng-ji-shu-zu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ConstructArr {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        int[] ints = new ConstructArr().constructArr(arr);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public int[] constructArr(int[] a) {
        if (a == null || a.length == 0) return a;
        int n = a.length;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = right[n - 1] = 1;

        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * a[i - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * a[i + 1];
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = left[i] * right[i];
        }
        return ans;
    }
}
