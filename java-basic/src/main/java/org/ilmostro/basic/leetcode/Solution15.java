package org.ilmostro.basic.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 *
 *  
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/fu-za-lian-biao-de-fu-zhi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution15 {

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if(head == null){
            return null;
        }

        Node curr = head;
        //key: old node, value: new node
        Map<Node, Node> map = new HashMap<>();
        while (curr != null) {
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        curr = head;
        while(curr != null){
            Node node = map.get(curr);
            node.next = map.get(curr.next);
            node.random = map.get(curr.random);
            curr = curr.next;
        }
        return map.get(head);
    }
}
