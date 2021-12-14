package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.List;

/**
 * 实现一个函数，检查一棵二叉树是否为二叉搜索树。
 *
 * 示例 1:
 * 输入:
 *     2
 *    / \
 *   1   3
 * 输出: true
 * 示例 2:
 * 输入:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/legal-binary-search-tree-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution15 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(20);

//        TreeNode root = new TreeNode(2);
//        root.left = new TreeNode(1);
//        root.right = new TreeNode(3);

        boolean validBST = new Solution15().isValidBST(root);
        System.out.println(validBST);
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public void isValidBST(TreeNode root, List<Integer> list) {
        if(root == null){
            return;
        }
        isValidBST(root.left, list);
        list.add(root.val);
        isValidBST(root.right, list);
    }

    public boolean isValidBST(TreeNode root, Integer min, Integer max){
        if(root == null){
            return true;
        }
        if((max != null && root.val >= max) || (min != null && root.val <= min)){
            return false;
        }
        return isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max);
    }

}
