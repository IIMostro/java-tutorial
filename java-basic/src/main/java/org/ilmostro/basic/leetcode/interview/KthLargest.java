package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.PriorityQueue;

/**
 * 给定一棵二叉搜索树，请找出其中第 k 大的节点的值。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: root = [3,1,4,null,2], k = 1
 * 3
 * / \
 * 1   4
 * \
 *    2
 * 输出: 4
 * 示例 2:
 * <p>
 * 输入: root = [5,3,6,2,4,null,null,1], k = 3
 * 5
 * / \
 * 3   6
 * / \
 * 2   4
 * /
 * 1
 * 输出: 4
 *  
 * <p>
 * 限制：
 * <p>
 * 1 ≤ k ≤ 二叉搜索树元素个数
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-di-kda-jie-dian-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class KthLargest {

    public static void main(String[] args) {
//        TreeNode root = new TreeNode(5);
//        TreeNode node1 = new TreeNode(3);
//        TreeNode node2 = new TreeNode(6);
//        TreeNode node3 = new TreeNode(2);
//        TreeNode node4 = new TreeNode(4);
//        TreeNode node5 = new TreeNode(1);
//
//        root.left = node1;
//        root.right = node2;
//        node1.left = node3;
//        node1.right = node4;
//        node3.left = node5;
//
//        int i = new KthLargest().kthLargest(root, 2);
//        System.out.println(i);


        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        kthLargest.add(3);
        kthLargest.add(5);
        kthLargest.add(10);
        kthLargest.add(9);
        kthLargest.add(5);
    }

    public KthLargest() {
    }

    public int kthLargest(TreeNode root, int k) {
        return normal(root, k);
    }

    int k, res;

    public int normal(TreeNode root, int k) {
        this.k = k;
        dfs(root);
        return res;
    }

    public void dfs(TreeNode root) {
        if (root == null) return;
        dfs(root.right);
        if (k == 0) return;
        if (--k == 0) res = root.val;
        dfs(root.left);
    }

    /**
     * 设计一个找到数据流中第 k 大元素的类（class）。注意是排序后的第 k 大元素，不是第 k 个不同的元素。
     *
     * 请实现 KthLargest 类：
     *
     * KthLargest(int k, int[] nums) 使用整数 k 和整数流 nums 初始化对象。
     * int add(int val) 将 val 插入数据流 nums 后，返回当前数据流中第 k 大的元素。
     *  
     *
     * 示例：
     *
     * 输入：
     * ["KthLargest", "add", "add", "add", "add", "add"]
     * [[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
     * 输出：
     * [null, 4, 5, 5, 8, 8]
     *
     * 解释：
     * KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
     * kthLargest.add(3);   // return 4
     * kthLargest.add(5);   // return 5
     * kthLargest.add(10);  // return 5
     * kthLargest.add(9);   // return 8
     * kthLargest.add(4);   // return 8
     *  
     *
     * 提示：
     * 1 <= k <= 104
     * 0 <= nums.length <= 104
     * -104 <= nums[i] <= 104
     * -104 <= val <= 104
     * 最多调用 add 方法 104 次
     * 题目数据保证，在查找第 k 大元素时，数组中至少有 k 个元素
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-a-stream
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    int K;
    public KthLargest(int k, int[] nums) {
        this.K = k;
        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        if(queue.size() < this.K || val > queue.peek()){
            queue.offer(val);
        }
        if(queue.size() > this.K){
            queue.poll();
        }
        return queue.peek();
    }
}
