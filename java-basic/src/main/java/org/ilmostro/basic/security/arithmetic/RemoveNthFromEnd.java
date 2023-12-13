package org.ilmostro.basic.security.arithmetic;

/**
 * @author li.bowei
 */
public class RemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        new RemoveNthFromEnd().removeNthFromEnd(head, 1);
    }

    /**
     * 删除倒数的节点
     *
     * @param head 节点
     * @param n 倒数第几个
     * @return listNode
     */
    private ListNode removeNthFromEnd(ListNode head, int n){
        ListNode listNode = new ListNode(0, head);

        int length = 0;
        while(head.next != null){
            ++length;
            head = head.next;
        }

        //用于标注头节点
        ListNode current = listNode;
        //找到倒数的位置
        for(int i = 1; i < length - n + 1; i ++){
            current = current.next;
        }
        //删除这个节点
        current.next = current.next.next;
        return listNode.next;
    }

    private static class ListNode{
        private int val;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
