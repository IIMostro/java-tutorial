package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import org.ilmostro.basic.algorithm.ListNode;

/**
 *
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 *
 *
 * @link {https://leetcode-cn.com/problems/swap-nodes-in-pairs/}
 * @author li.bowei
 */
public class SwapNodesInPairs {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(new int[]{1, 2, 3, 4});
        new SwapNodesInPairs().swapPairs(listNode);
        System.out.println(listNode);
    }

    public ListNode swapPairsR(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairsR(next.next);
        next.next = head;
        return next;
    }

    public ListNode swapPairs(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }
        ListNode dummy = new ListNode(0, head);
        ListNode curr = dummy;
        while(curr.next != null && curr.next.next != null){
            ListNode first = curr.next;
            ListNode second = curr.next.next;

//            curr.next.next.next =
            first.next = second.next;
            second.next = first;
            curr.next = second;
            curr = curr.next.next;
        }
        return dummy.next;
    }
}
