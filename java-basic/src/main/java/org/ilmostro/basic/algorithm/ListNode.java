package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(int[] arr){
        this.val = arr[0];
        ListNode curr = this;
        for(int i = 1; i < arr.length; i++){
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListNode: ");
        ListNode curr = this;
        while(curr != null){
            sb.append(curr.val);
            sb.append(" ->");
            curr = curr.next;
        }
        sb.append("NULL");
        return sb.toString();
    }
}
