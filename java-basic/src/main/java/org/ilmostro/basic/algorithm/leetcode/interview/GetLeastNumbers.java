package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：arr = [3,2,1], k = 2
 * 输出：[1,2] 或者 [2,1]
 * 示例 2：
 * <p>
 * 输入：arr = [0,1,2,1], k = 1
 * 输出：[0]
 *  
 * <p>
 * 限制：
 * <p>
 * 0 <= k <= arr.length <= 10000
 * 0 <= arr[i] <= 10000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class GetLeastNumbers {

    public static void main(String[] args) {
        int[] nums = {3, 2, 1};
        int[] leastNumbers = new GetLeastNumbers().getLeastNumbers(nums, 2);
        System.out.println();
    }

    public int[] getLeastNumbers(int[] arr, int k) {
        return priority(arr, k);
    }

    public int[] normal(int[] arr, int k) {
        if (arr == null || arr.length <= 0 || arr.length <= k) {
            return arr;
        }
        Arrays.sort(arr);
        int[] result = new int[k];
        System.arraycopy(arr, 0, result, 0, k);
        return result;
    }

    public int[] priority(int[] arr, int k) {
        if (arr == null || arr.length <= 0 || arr.length <= k) return arr;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : arr) {
            queue.offer(num);
        }
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = queue.poll();
        }
        return result;
    }


    /**
     * 快排思想
     *
     * @param arr 数组
     * @param k   第几位
     * @return 前几位
     */
    public int[] findLeastNumbers(int[] arr, int k) {
        findK(arr, 0, arr.length - 1, k);
        int[] result = new int[k];
        System.arraycopy(arr, 0, result, 0, k);
        return result;
    }

    public void findK(int[] arr, int l, int r, int target) {
        if (l >= r) {
            return;
        }
        int pos = partition(arr, l, r);
        int num = pos - l + 1;
        if (num == target) {
            return;
        }
        if (target > num) {
            findK(arr, pos + 1, r, target - num);
        } else {
            findK(arr, l, pos - 1, target);
        }
    }

    public int partition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; ++j) {
            if (arr[j] <= pivot) {
                swap(arr, ++i, j);
            }
        }
        swap(arr, i + 1, r);
        return i + 1;
    }

    public void swap(int[] arr, int index, int target) {
        if (index == target) return;
        arr[index] = arr[target] ^ arr[index];
        arr[target] = arr[target] ^ arr[index];
        arr[index] = arr[target] ^ arr[index];
    }

}
