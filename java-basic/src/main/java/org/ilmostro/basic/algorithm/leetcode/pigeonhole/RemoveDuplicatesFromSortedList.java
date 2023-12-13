package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import org.ilmostro.basic.algorithm.ListNode;

/**
 *
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
 *
 * 返回同样按升序排列的结果链表。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/}
 */
public class RemoveDuplicatesFromSortedList {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(new int[]{1, 2,2,2,2,2, 3, 4});
        System.out.println(deleteDuplicates2(listNode));
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        ListNode result = new ListNode();
        ListNode res = result;
        while(current != null){
            int val = current.val;
            ListNode next = current.next;
            while(next != null && next.val == val){
                next = next.next;
            }
            result.next = new ListNode(current.val);
            result = result.next;
            current = next;
        }
        return res.next;
    }

    public static ListNode deleteDuplicates2(ListNode head) {
        ListNode current = head;
        while(current.next != null){
            if(current.val == current.next.val){
                current.next = current.next.next;
            }else {
                current = current.next;
            }
        }
        return head;
    }
}
