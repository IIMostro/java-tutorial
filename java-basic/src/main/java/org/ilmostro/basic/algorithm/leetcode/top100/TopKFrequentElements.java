package org.ilmostro.basic.algorithm.leetcode.top100;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 * <p>
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 105
 * k 的取值范围是 [1, 数组中不相同的元素的个数]
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
 *  
 * <p>
 * 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/top-k-frequent-elements
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class TopKFrequentElements {

    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,3};
        int[] ints = new TopKFrequentElements().topKFrequent(nums, 2);
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Long> collect = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(v1 -> v1, Collectors.counting()));
        PriorityQueue<Map.Entry<Integer, Long>> queue = new PriorityQueue<>((v1, v2) -> Long.valueOf(v1.getValue() - v2.getValue()).intValue());
        for (Map.Entry<Integer, Long> entry : collect.entrySet()) {
            if (queue.size() < k) queue.offer(entry);
            else {
                if (queue.peek().getValue() < entry.getValue()) {
                    queue.poll();
                    queue.offer(entry);
                }
            }
        }
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = queue.poll().getKey();
        }
        return result;
    }
}
