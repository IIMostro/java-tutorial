package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 给你一棵二叉搜索树，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
 *
 *  
 *
 * 示例 1：
 *
 *
 * 输入：root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
 * 输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
 * 示例 2：
 *
 *
 * 输入：root = [5,1,7]
 * 输出：[1,null,5,null,7]
 *  
 *
 * 提示：
 *
 * 树中节点数的取值范围是 [1, 100]
 * 0 <= Node.val <= 1000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/increasing-order-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class IncreasingOrderSearchTree {

    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        recursion(root, list);
        TreeNode ans = new TreeNode();
        TreeNode curr = ans;
        for (Integer integer : list) {
            curr.right = new TreeNode(integer);
            curr = curr.right;
        }
        return ans.right;
    }

    public void recursion(TreeNode root, List<Integer> curr){
        if(root == null) return;
        recursion(root.left, curr);
        curr.add(root.val);
        recursion(root.right, curr);
    }

    public List<Integer> normal(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;
        Deque<TreeNode> deque = new LinkedList<>();
        while(!deque.isEmpty() || root != null){
            while(root != null){
                deque.push(root);
                root = root.left;
            }
            root = deque.pop();
            result.add(root.val);
            root = root.right;
        }
        return result;
    }
}
