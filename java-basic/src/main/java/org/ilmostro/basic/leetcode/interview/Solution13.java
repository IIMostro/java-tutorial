package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;
import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 输入：[1,2,3,4,5,null,7,8]
 * <p>
 * 1
 * /  \
 * 2    3
 * / \    \
 * 4   5    7
 * /
 * 8
 * <p>
 * 输出：[[1],[2,3],[4,5,7],[8]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/list-of-depth-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution13 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);

        ListNode[] listNodes = new Solution13().listOfDepth(root);
        System.out.println(listNodes);
    }

    public ListNode[] listOfDepth(TreeNode tree) {

        if (tree == null) {
            return new ListNode[0];
        }

        List<ListNode> ret = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(tree);
        while (!queue.isEmpty()) {
            ListNode dummy = new ListNode();
            ListNode curr = dummy;
            for (int i = queue.size(); i > 0; i--) {
                TreeNode next = queue.poll();
                curr.next = new ListNode(next.val);
                curr = curr.next;
                if(next.left != null) queue.offer(next.left);
                if(next.right != null) queue.offer(next.right);
            }
            ret.add(dummy.next);
        }
        return ret.toArray(ListNode[]::new);
    }
}
