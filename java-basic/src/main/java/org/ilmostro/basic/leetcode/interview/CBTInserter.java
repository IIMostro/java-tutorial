package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author li.bowei
 */
public class CBTInserter {

    List<TreeNode> nodes = new ArrayList<>();

    public CBTInserter(TreeNode root) {
        if (root == null) throw new RuntimeException();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            nodes.add(curr);
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
    }

    public int insert(int v) {
        TreeNode node = new TreeNode(v);
        nodes.add(node);
        int parent = nodes.size() / 2 - 1;
        if (nodes.size() % 2 == 0) {
            nodes.get(parent).left = node;
        } else {
            nodes.get(parent).right = node;
        }
        return nodes.get(parent).val;
    }

    public TreeNode get_root() {
        return nodes.isEmpty() ? null : nodes.get(0);
    }
}
