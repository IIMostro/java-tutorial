package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author li.bowei
 */
public class BSTIterator {

    Queue<Integer> queue;

    public BSTIterator(TreeNode root) {
        queue = new LinkedList<>();
        dfs(root);
    }

    public void dfs(TreeNode root) {
        if (root == null) return;
        dfs(root.left);
        queue.offer(root.val);
        dfs(root.right);
    }

    public int next() {
        return queue.isEmpty() ? -1 : queue.poll();
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
