package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.Stack;

/**
 *
 * 设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
 *
 * 如果指定节点没有对应的“下一个”节点，则返回null。
 *
 * 示例 1:
 *
 * 输入: root = [2,1,3], p = 1
 *
 *   2
 *  / \
 * 1   3
 *
 * 输出: 2
 * 示例 2:
 *
 * 输入: root = [5,3,6,2,4,null,null,1], p = 6
 *
 *       5
 *      / \
 *     3   6
 *    / \
 *   2   4
 *  /
 * 1
 *
 * 输出: null
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/successor-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution16 {

    public static void main(String[] args) {

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        TreeNode treeNode = new Solution16().inorderSuccessor(root, root.left);
        System.out.println(treeNode.val);
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        boolean next = false;
        while(curr != null || !stack.isEmpty()){
            while(curr != null){
                stack.push(curr);
                curr = curr.left;
            }
            if(!stack.isEmpty()){
                curr = stack.pop();
                if(next){
                    return curr;
                }
                if(curr == p){
                    next = true;
                }
                curr = curr.right;
            }
        }
        return null;
    }
}
