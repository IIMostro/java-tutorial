package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 *
 * 叶子节点 是指没有子节点的节点。
 *
 *  
 *
 * 示例 1：
 *
 *
 *
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：[[5,4,11,2],[5,8,4,5]]
 * 示例 2：
 *
 *
 *
 * 输入：root = [1,2,3], targetSum = 5
 * 输出：[]
 * 示例 3：
 *
 * 输入：root = [1,2], targetSum = 0
 * 输出：[]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class PathSum {

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        return normal(root, target);
    }

    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> normal(TreeNode root, int target){
        dfs(root, target);
        return res;
    }

    public void dfs(TreeNode root, int target){
        if(root == null) return;
        path.add(root.val);
        target -= root.val;
        if(target == 0 && root.left == null && root.right == null) res.add(new ArrayList<>(path));
        dfs(root.left, target);
        dfs(root.right, target);
        path.remove(path.size() - 1);
    }

}
