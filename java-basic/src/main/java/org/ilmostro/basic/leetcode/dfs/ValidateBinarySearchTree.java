package org.ilmostro.basic.leetcode.dfs;

import org.ilmostro.basic.algorithm.TreeNode;

/**
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 * <p>
 * 有效 二叉搜索树定义如下：
 * <p>
 * 节点的左子树只包含 小于 当前节点的数。
 * 节点的右子树只包含 大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：root = [2,1,3]
 * 输出：true
 * 示例 2：
 * <p>
 * <p>
 * 输入：root = [5,1,4,null,null,3,6]
 * 输出：false
 * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ValidateBinarySearchTree {

    public static void main(String[] args) {
        boolean validBST = new ValidateBinarySearchTree().isValidBST(new TreeNode(Integer.MAX_VALUE));
        System.out.println(validBST);
    }

    public boolean isValidBST(TreeNode root) {
        return normal(root);
    }

    long pre = Long.MIN_VALUE;

    public boolean normal(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean normal = normal(root.left);
        if (root.val <= pre) return false;
        pre = root.val;
        boolean normal1 = normal(root.right);
        return normal && normal1;
    }
}
