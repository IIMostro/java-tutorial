package org.ilmostro.basic.leetcode.dfs;

import org.ilmostro.basic.algorithm.TreeNode;

/**
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * <p>
 * 本题中，一棵高度平衡二叉树定义为：
 * <p>
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：true
 * 示例 2：
 * <p>
 * <p>
 * 输入：root = [1,2,2,3,3,null,null,4,4]
 * 输出：false
 * 示例 3：
 * <p>
 * 输入：root = []
 * 输出：true
 *  
 * <p>
 * 提示：
 * <p>
 * 树中的节点数在范围 [0, 5000] 内
 * -104 <= Node.val <= 104
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/balanced-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class BalancedBinaryTree {

    public static void main(String[] args) {
        boolean balanced = new BalancedBinaryTree().isBalanced(new TreeNode(1));
        System.out.println(balanced);
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (!(isBalanced(root.left) && isBalanced(root.right))) {
            return false;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        return Math.abs(left - right) <= 1;
    }

    public int depth(TreeNode root) {
        if(root == null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        return Math.max(left, right) + 1;
    }
}
