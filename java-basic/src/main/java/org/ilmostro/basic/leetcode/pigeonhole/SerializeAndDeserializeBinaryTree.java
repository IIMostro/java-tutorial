package org.ilmostro.basic.leetcode.pigeonhole;

import org.ilmostro.basic.algorithm.TreeNode;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
 * <p>
 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 * <p>
 * 提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SerializeAndDeserializeBinaryTree {

    public static void main(String[] args) {
        String data = "[1,2,null,3]";
        String[] split = data.substring(1, data.length() - 1).split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";
        StringBuilder res = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                res.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            } else res.append("null,");
        }
        if (res.charAt(res.length() - 1) == ',') {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(Objects.isNull(data) || data.equals("")){
            return null;
        }
        String[] split = data.substring(1, data.length() - 2).split(",");
        if (split.length <= 0) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0;
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(!split[++size].equals("null")){
                node.left = new TreeNode(Integer.parseInt(split[size]));
                queue.offer(node.left);
            }
            if(!split[++size].equals("null")){
                node.right = new TreeNode(Integer.parseInt(split[size]));
                queue.offer(node.right);
            }
        }
        return root;
    }
}
