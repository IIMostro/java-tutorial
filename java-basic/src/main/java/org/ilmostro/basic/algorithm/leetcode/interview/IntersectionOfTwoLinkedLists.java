package org.ilmostro.basic.algorithm.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;

/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/intersection-of-two-linked-lists-lcci/}
 */
public class IntersectionOfTwoLinkedLists {

    public static void main(String[] args) {

        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(8);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(5);
        ListNode node7 = new ListNode(0);
        ListNode node8 = new ListNode(1);

        node4.next = node5;
        node3.next = node4;

        node8.next = node3;
        node7.next = node8;
        node6.next = node7;

        node1.next = node2;
        node2.next = node3;


        IntersectionOfTwoLinkedLists solution = new IntersectionOfTwoLinkedLists();
        ListNode intersectionNode = solution.getIntersectionNode(node1, node6);
        System.out.println(intersectionNode);
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        return forcible(headA, headB);
    }

    public ListNode forcible(ListNode headA, ListNode headB) {
        ListNode h1 = headA;
        ListNode h2 = headB;
        while (h1 != h2) {
            h1 = h1 == null ? headB : h1.next;
            h2 = h2 == null ? headA : h2.next;
        }
        return h1;
    }
}
