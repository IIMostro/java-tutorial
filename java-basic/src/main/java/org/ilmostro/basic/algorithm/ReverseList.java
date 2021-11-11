package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class ReverseList {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        ListNode listNode = new ListNode(arr);
        ListNode recursion = new ReverseList().reverseListV1(listNode);
        System.out.println(recursion);
    }

    public ListNode reverseList(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode curr = head;
        ListNode prev = null;
        while(curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public ListNode reverseListV1(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode ret = reverseListV1(head.next);
        head.next.next = head;
        head.next = null;
        return ret;
    }

    private ListNode recursion(ListNode current, ListNode prev){
        if(current == null){
            return prev;
        }
        ListNode next = current.next;
        current.next = prev;
        return recursion(next, current);
    }
}
