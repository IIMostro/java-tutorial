package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import org.ilmostro.basic.algorithm.ListNode;

/**
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-linked-list-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class ReverseLinkedList_II {

    public static void main(String[] args) {
        ListNode node = new ListNode(new int[]{1, 2, 3, 4, 5});
        ListNode listNode = new ReverseLinkedList_II().reverseBetween(node, 2,4);
        System.out.println(listNode);
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {

        ListNode dummyNode = new ListNode(-1, head);
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }

        ListNode leftNode = pre.next;
        ListNode curr = rightNode.next;

        pre.next = null;
        rightNode.next = null;
        reverseListNode(leftNode);
        pre.next = rightNode;
        leftNode.next = curr;
        return dummyNode.next;
    }

    public ListNode reverseListNode(ListNode listNode) {
        ListNode curr = listNode;
        ListNode pre = null;
        int size = 0;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
            size++;
        }
        return pre;
    }
}
