package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.*;

/**
 *
 * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
 *
 *  
 *
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其层次遍历结果：
 *
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 *  
 *
 * 提示：
 *
 * 节点总数 <= 1000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-ii-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LevelOrder_II {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(7);

        List<List<Integer>> lists = new LevelOrder_II().levelOrder(root);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {

        if(root == null){
            return Collections.emptyList();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            List<Integer> temp = new ArrayList<>();
            for(int i = queue.size(); i > 0; i--) {
                TreeNode next = queue.poll();
                temp.add(next.val);
                if(next.left != null) queue.offer(next.left);
                if(next.right != null) queue.offer(next.right);
            }
            result.add(temp);
        }
        return result;
    }
}
