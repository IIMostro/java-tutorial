package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 * <p>
 * 例如，
 * <p>
 * [2,3,4] 的中位数是 3
 * <p>
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 * <p>
 * 设计一个支持以下两种操作的数据结构：
 * <p>
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例 1：
 * <p>
 * 输入：
 * ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
 * [[],[1],[2],[],[3],[]]
 * 输出：[null,null,null,1.50000,null,2.00000]
 * 示例 2：
 * <p>
 * 输入：
 * ["MedianFinder","addNum","findMedian","addNum","findMedian"]
 * [[],[2],[],[3],[]]
 * 输出：[null,null,2.00000,null,2.50000]
 *  
 * <p>
 * 限制：
 * <p>
 * 最多会对 addNum、findMedian 进行 50000 次调用。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MedianFinder {

    //数字比较大的一边，这里用最小堆
    Queue<Integer> high;
    //数字比较小的一边，这里用最大堆
    Queue<Integer> low;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        high = new PriorityQueue<>();
        low = new PriorityQueue<>((v1, v2) -> v2 - v1);
    }

    public void addNum(int num) {
        if (high.size() != low.size()) {
            high.offer(num);
            low.offer(high.poll());
        } else {
            low.offer(num);
            high.offer(low.poll());
        }
    }

    public double findMedian() {
        return low.size() != high.size() ? high.peek() : (low.peek() + high.peek()) / 2.0;
    }
}
