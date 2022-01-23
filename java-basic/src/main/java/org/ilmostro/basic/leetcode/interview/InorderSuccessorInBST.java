package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * 给定一棵二叉搜索树和其中的一个节点 p ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
 *
 * 节点 p 的后继是值比 p.val 大的节点中键值最小的节点，即按中序遍历的顺序节点 p 的下一个节点。
 *
 *  
 *
 * 示例 1：
 *
 *
 *
 * 输入：root = [2,1,3], p = 1
 * 输出：2
 * 解释：这里 1 的中序后继是 2。请注意 p 和返回值都应是 TreeNode 类型。
 * 示例 2：
 *
 *
 *
 * 输入：root = [5,3,6,2,4,null,null,1], p = 6
 * 输出：null
 * 解释：因为给出的节点没有中序后继，所以答案就返回 null 了。
 *  
 *
 * 提示：
 *
 * 树中节点的数目在范围 [1, 104] 内。
 * -105 <= Node.val <= 105
 * 树中各节点的值均保证唯一。
 *  
 *
 * 注意：本题与主站 285 题相同： https://leetcode-cn.com/problems/inorder-successor-in-bst/
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/P5rCT8
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class InorderSuccessorInBST {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        TreeNode treeNode = new InorderSuccessorInBST().inorderSuccessor(root, root.left);
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        recursion(root, p);
        return ans;
    }

    public TreeNode normal(TreeNode root, TreeNode p){
        if(root == null || p == null) return null;
        Deque<TreeNode> deque = new LinkedList<>();
        TreeNode curr = root;
        boolean next = false;
        while(!deque.isEmpty() || curr != null){
            while(curr != null){
                deque.push(curr);
                curr = curr.left;
            }
            curr = deque.pop();
            if (next) return curr;
            if (curr == p) next = true;
            curr = curr.right;
        }
        return null;
    }

    TreeNode pre = null;
    TreeNode ans = null;
    public void recursion(TreeNode root, TreeNode p){
        if(root == null) return;
        recursion(root.left, p);
        if(pre == p) ans = root;
        pre = root;
        recursion(root.right, p);
    }
}
