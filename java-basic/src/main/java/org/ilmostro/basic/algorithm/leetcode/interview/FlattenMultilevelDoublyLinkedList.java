package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 多级双向链表中，除了指向下一个节点和前一个节点指针之外，它还有一个子链表指针，可能指向单独的双向链表。这些子列表也可能会有一个或多个自己的子项，依此类推，生成多级数据结构，如下面的示例所示。
 * <p>
 * 给你位于列表第一级的头节点，请你扁平化列表，使所有结点出现在单级双链表中。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 * 输出：[1,2,3,7,8,11,12,9,10,4,5,6]
 * 解释：
 * <p>
 * 输入的多级列表如下图所示：
 * <p>
 * <p>
 * <p>
 * 扁平化后的链表如下图：
 * <p>
 * <p>
 * 示例 2：
 * <p>
 * 输入：head = [1,2,null,3]
 * 输出：[1,3,2]
 * 解释：
 * <p>
 * 输入的多级列表如下图所示：
 * <p>
 * 1---2---NULL
 * |
 * 3---NULL
 * 示例 3：
 * <p>
 * 输入：head = []
 * 输出：[]
 *  
 * <p>
 * 如何表示测试用例中的多级链表？
 * <p>
 * 以 示例 1 为例：
 * <p>
 * 1---2---3---4---5---6--NULL
 * |
 * 7---8---9---10--NULL
 * |
 * 11--12--NULL
 * 序列化其中的每一级之后：
 * <p>
 * [1,2,3,4,5,6,null]
 * [7,8,9,10,null]
 * [11,12,null]
 * 为了将每一级都序列化到一起，我们需要每一级中添加值为 null 的元素，以表示没有节点连接到上一级的上级节点。
 * <p>
 * [1,2,3,4,5,6,null]
 * [null,null,7,8,9,10,null]
 * [null,11,12,null]
 * 合并所有序列化结果，并去除末尾的 null 。
 * <p>
 * [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 *  
 * <p>
 * 提示：
 * <p>
 * 节点数目不超过 1000
 * 1 <= Node.val <= 10^5
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flatten-a-multilevel-doubly-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class FlattenMultilevelDoublyLinkedList {

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        Node head2 = new Node(2);
        Node head3 = new Node(3);
        Node head4 = new Node(4);
        Node head5 = new Node(5);
        Node head6 = new Node(6);
        Node head7 = new Node(7);
        Node head8 = new Node(8);
        Node head9 = new Node(9);
        Node head10 = new Node(10);
        Node head11 = new Node(11);
        Node head12 = new Node(12);

        head1.next = head2;
        head2.prev = head1;
        head2.next = head3;
        head3.prev = head2;
        head3.next = head4;
        head4.prev = head3;
        head4.next = head5;
        head5.prev = head4;
        head5.next = head6;
        head6.prev = head5;

        head3.child = head7;
        head7.next = head8;
        head8.prev = head7;
        head8.child = head11;
        head11.next = head12;
        head12.prev = head11;
        head8.next = head9;
        head9.next = head10;
        head10.prev = head9;

        Node flatten = new FlattenMultilevelDoublyLinkedList().flatten(head1);
    }

    public Node flatten(Node head) {
        return recursion(head);
    }

    public Node stack(Node head) {
        if (head == null || (head.next == null && head.child == null)) return head;
        Deque<Node> deque = new LinkedList<>();
        deque.offerLast(head);
        Node pre = new Node();
        Node curr = pre;
        while (!deque.isEmpty()) {
            Node node = deque.pollLast();
            Node newNode = new Node();
            newNode.val = node.val;
            newNode.prev = curr;
            curr.next = newNode;
            curr = newNode;
            if (node.next != null) deque.offerLast(node.next);
            if (node.child != null) deque.offerLast(node.child);
        }
        Node next = pre.next;
        next.prev = null;
        return next;
    }

    public Node recursion(Node head) {
        dfs(head);
        return head;
    }

    public Node dfs(Node head){
        Node last = head;
        while (head != null) {
            if (head.child == null) {
                last = head;
                head = head.next;
            } else {
                Node tmp = head.next;
                Node childLast = dfs(head.child);
                head.next = head.child;
                head.child.prev = head;
                head.child = null;
                if (childLast != null) childLast.next = tmp;
                if (tmp != null) tmp.prev = childLast;
                last = head;
                head = childLast;
            }
        }
        return last;
    }
}
