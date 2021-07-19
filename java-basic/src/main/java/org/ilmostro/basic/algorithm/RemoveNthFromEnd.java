package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class RemoveNthFromEnd {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode currentNode = head;
        ListNode secondListNode = dummy.next;
        for (int i = 0; i < n; i++) {
            currentNode = currentNode.next;
        }

        if(currentNode == null){
            return secondListNode.next;
        }

        while(currentNode.next != null){
            currentNode = currentNode.next;
            secondListNode = secondListNode.next;
        }
        secondListNode.next = secondListNode.next.next;
        return dummy.next;
    }


    public static void main(String[] args) {

        ListNode listNode = new ListNode(new int[]{1});
        ListNode listNode1 = new RemoveNthFromEnd().removeNthFromEnd(listNode, 1);
        log.info("list:{}", listNode1);
    }
}
