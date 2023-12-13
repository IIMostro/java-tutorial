package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;

import java.util.Stack;

/**
 * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * <p>
 *  
 * <p>
 * 示例1：
 * <p>
 * <p>
 * <p>
 * 输入：l1 = [7,2,4,3], l2 = [5,6,4]
 * 输出：[7,8,0,7]
 * 示例2：
 * <p>
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[8,0,7]
 * 示例3：
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 *  
 * <p>
 * 提示：
 * <p>
 * 链表的长度范围为 [1, 100]
 * 0 <= node.val <= 9
 * 输入数据保证链表代表的数字无前导 0
 *  
 * <p>
 * 进阶：如果输入链表不能翻转该如何解决？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class AddTwoNumbers_II {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(new int[]{7, 2, 4, 3});
        ListNode l2 = new ListNode(new int[]{5, 6, 4});
        ListNode listNode = new AddTwoNumbers_II().addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return normal(l1, l2);
    }

    public ListNode normal(ListNode l1, ListNode l2){
        l1 = reversal(l1);
        l2 = reversal(l2);
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        int scale = 0;
        while (l1 != null || l2 != null || scale != 0) {
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;
            int sum = v1 + v2 + scale;
            scale = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return reversal(dummy.next);
    }

    public ListNode reversal(ListNode root){
        if(root == null || root.next == null) return root;
        ListNode ret = reversal(root.next);
        root.next.next = root;
        root.next = null;
        return ret;
    }

    public ListNode stack(ListNode l1, ListNode l2){
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        fill(stack1, l1);
        fill(stack2, l2);

        int scale = 0;
        ListNode dummy = null;
        while (!stack1.isEmpty() || !stack2.isEmpty() || scale != 0) {
            int v1 = stack1.isEmpty() ? 0 : stack1.pop();
            int v2 = stack2.isEmpty() ? 0 : stack2.pop();
            int sum = v1 + v2 + scale;
            scale = sum / 10;
            ListNode node = new ListNode(sum % 10);
            node.next = dummy;
            dummy = node;
        }
        return dummy;
    }

    public void fill(Stack<Integer> stack, ListNode root) {
        while (root != null) {
            stack.push(root.val);
            root = root.next;
        }
    }
}
