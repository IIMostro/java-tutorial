package org.ilmostro.basic.leetcode.dynamic;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：n = 3
 * 输出：[[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：[[1]]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= n <= 8
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-binary-search-trees-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class UniqueBinarySearchTrees_II {

    public static void main(String[] args) {
        List<TreeNode> treeNodes = new UniqueBinarySearchTrees_II().generateTrees(3);
        for (TreeNode treeNode : treeNodes) {
            TreeNode.show(treeNode);
        }
    }

    public List<TreeNode> generateTrees(int n) {
        if(n <= 0) return Collections.emptyList();
        return dfs(1, n);
    }

    public List<TreeNode> dfs(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        if (start > end) {
            result.add(null);
            return result;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> left = dfs(start, i - 1);
            List<TreeNode> right = dfs(i + 1, end);

            for (TreeNode leftNode : left) {
                for (TreeNode rightNode : right) {
                    TreeNode curr = new TreeNode(i);
                    curr.left = leftNode;
                    curr.right = rightNode;
                    result.add(curr);
                }
            }
        }
        return result;
    }
}
