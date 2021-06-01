package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class ReverseListNode {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;

        reverseList(listNode1);
    }

    private static class ListNode{
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    // 1 -> 2 -> 3 -> 4 -> null
    public static ListNode reverseList(ListNode head) {
        //使用双指针，最开始的前一个第一次肯定为空
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
