package org.ilmostro.basic.leetcode.pigeonhole;

import org.ilmostro.basic.algorithm.ListNode;

/**
 * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/remove-linked-list-elements/}
 */
public class RemoveLinkedListElements {

    public static void main(String[] args) {
        ListNode target = new ListNode(new int[]{1, 2, 6, 3, 4, 5, 6});
        ListNode listNode = new RemoveLinkedListElements().removeElements(target, 6);
        System.out.println(listNode);
    }

    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        head.next = removeElements(head.next, val);
        return head.val == val ? head.next : head;
    }
}
