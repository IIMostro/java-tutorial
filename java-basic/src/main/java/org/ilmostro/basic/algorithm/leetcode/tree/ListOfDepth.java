package org.ilmostro.basic.algorithm.leetcode.tree;

import org.ilmostro.basic.algorithm.ListNode;
import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 面试题 04.03. 特定深度节点链表
 * 给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组。
 * <p>
 * <p>
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
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/list-of-depth-lcci/}
 */
public class ListOfDepth {

    public static void main(String[] args) {
        TreeNode tree = new TreeNode(1);
        tree.left = new TreeNode(2);
        tree.right = new TreeNode(3);
        tree.left.left = new TreeNode(4);
        tree.left.right = new TreeNode(5);
        tree.right.right = new TreeNode(7);
        tree.left.left.left = new TreeNode(8);

        ListNode[] listNodes = new ListOfDepth().listOfDepth(tree);
        for (ListNode listNode : listNodes) {
            System.out.println(listNode);
        }
    }

    public ListNode[] listOfDepth(TreeNode tree) {
        List<ListNode> ans = new ArrayList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        if(tree == null) return null;
        deque.offer(tree);
        while (!deque.isEmpty()) {
            ListNode dummy = new ListNode();
            ListNode curr = dummy;
            for (int i = deque.size(); i > 0; i--) {
                TreeNode poll = deque.poll();
                curr.next = new ListNode(poll.val);
                curr = curr.next;
                if(poll.left != null) deque.offerLast(poll.left);
                if(poll.right != null) deque.offerLast(poll.right);
            }
            ans.add(dummy.next);
        }
        return ans.toArray(new ListNode[0]);
    }
}
