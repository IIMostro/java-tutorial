package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;

/**
 * 给定两个用链表表示的整数，每个节点包含一个数位。
 * <p>
 * 这些数位是反向存放的，也就是个位排在链表首部。
 * <p>
 * 编写函数对这两个整数求和，并用链表形式返回结果。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
 * 输出：2 -> 1 -> 9，即912
 * 进阶：思考一下，假设这些数位是正向存放的，又该如何解决呢?
 * <p>
 * 示例：
 * <p>
 * 输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
 * 输出：9 -> 1 -> 2，即912
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-lists-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SumLists {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(new int[]{1, 2, 3});
        ListNode l2 = new ListNode(new int[]{2, 3, 4});
        ListNode root = new SumLists().addTwoNumbers(l1, l2);
        System.out.println(root);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return doAddPositive(l1, l2, 0);
    }

    public ListNode doAdd(ListNode l1, ListNode l2, int scale) {
        if (l1 == null && l2 == null) {
            return scale == 0 ? null : new ListNode(scale);
        }
        int sum = scale;
        if (l1 != null) {
            sum += l1.val;
        }
        if (l2 != null) {
            sum += l2.val;
        }
        ListNode root = new ListNode(sum % 10);
        root.next = doAdd(l1 != null ? l1.next : null, l2 != null ? l2.next : null, sum % 10 == sum ? 0 : 1);
        return root;
    }

    public ListNode doAddPositive(ListNode l1, ListNode l2, int scale) {
        if (l1 == null && l2 == null) {
            return scale == 0 ? null : new ListNode(scale);
        }
        int sum = scale;
        if (l1 != null) {
            sum += l1.val;
        }
        if (l2 != null) {
            sum += l2.val;
        }
        ListNode next = doAdd(l1 != null ? l1.next : null, l2 != null ? l2.next : null, sum % 10 == sum ? 0 : 1);
        ListNode root = new ListNode(sum % 10);
        root.next = next;
        return root;
    }
}
