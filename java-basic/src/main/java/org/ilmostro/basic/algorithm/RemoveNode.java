package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class RemoveNode {

    public static ListNode removeNode(ListNode node, int val){
        if(node == null){
            return null;
        }

        ListNode result = removeNode(node.next, val);
        if(node.val == val){
            return result;
        }else{
            node.next = result;
            return node;
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,6,4,5,6};
        ListNode listNode = new ListNode(arr);
        System.out.println(listNode);
        removeNode(listNode, 6);
        System.out.println(listNode);
    }
}
