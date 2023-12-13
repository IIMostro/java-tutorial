package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
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
 * 返回：
 *
 * [3,9,20,15,7]
 *  
 *
 * 提示：
 *
 * 节点总数 <= 1000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class LevelOrder {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(7);

        new LevelOrder().levelOrder(root);
    }

    public int[] levelOrder(TreeNode root) {

        if(root == null){
            return new int[]{};
        }
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> temp = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode poll = queue.poll();
            if(poll != null){
                temp.add(poll.val);
                queue.offer(poll.left);
                queue.offer(poll.right);
            }
        }

        int[] ret = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            ret[i] = temp.get(i);
        }
        return ret;
    }
}
