package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.*;

/**
 *
 * 给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
 *
 * 假设二叉树中至少有一个节点。
 *
 *  
 *
 * 示例 1:
 *
 *
 *
 * 输入: root = [2,1,3]
 * 输出: 1
 * 示例 2:
 *
 *
 *
 * 输入: [1,2,3,4,null,5,6,null,null,7]
 * 输出: 7
 *  
 *
 * 提示:
 *
 * 二叉树的节点个数的范围是 [1,104]
 * -231 <= Node.val <= 231 - 1 
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-bottom-left-tree-value
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class FindBottomLeftTreeValue {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        int bottomLeftValue = new FindBottomLeftTreeValue().findBottomLeftValue(root);
        System.out.println(bottomLeftValue);
    }

    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int value = root.val;
        while (!queue.isEmpty()) {
            int currSize = queue.size();
            for (int i = queue.size(); i > 0; i--) {
                if(i == currSize) value = queue.peek().val;
                TreeNode curr = queue.poll();
                if(curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue .offer(curr.right);
            }
        }
        return value;
    }
}
