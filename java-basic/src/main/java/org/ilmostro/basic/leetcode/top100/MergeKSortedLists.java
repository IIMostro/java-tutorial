package org.ilmostro.basic.leetcode.top100;

import org.ilmostro.basic.algorithm.ListNode;

import java.util.PriorityQueue;

/**
 * 给你一个链表数组，每个链表都已经按升序排列。
 * <p>
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * 示例 2：
 * <p>
 * 输入：lists = []
 * 输出：[]
 * 示例 3：
 * <p>
 * 输入：lists = [[]]
 * 输出：[]
 *  
 * <p>
 * 提示：
 * <p>
 * k == lists.length
 * 0 <= k <= 10^4
 * 0 <= lists[i].length <= 500
 * -10^4 <= lists[i][j] <= 10^4
 * lists[i] 按 升序 排列
 * lists[i].length 的总和不超过 10^4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class MergeKSortedLists {

    public static void main(String[] args) {
        ListNode[] test = new ListNode[3];
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(5);
        test[0] = listNode1;
        ListNode listNode2 = new ListNode(1);
        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);
        test[1] = listNode2;
        ListNode listNode3 = new ListNode(2);
        listNode3.next = new ListNode(6);
        test[2] = listNode3;

        ListNode listNode = new MergeKSortedLists().mergeKLists(new ListNode[]{new ListNode()});

    }

    public ListNode mergeKLists(ListNode[] lists) {
        return priority(lists);
    }

    public ListNode normal(ListNode[] lists) {
        if (lists == null || lists.length <= 0) return null;
        ListNode dummy = null;
        for (ListNode list : lists) {
            dummy = mergeTwo(dummy, list);
        }
        return dummy;
    }

    public ListNode mergeTwo(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
        if (l1.val > l2.val) {
            l2.next = mergeTwo(l1, l2.next);
            return l2;
        } else {
            l1.next = mergeTwo(l1.next, l2);
            return l1;
        }
    }

    static class ComparableListNode implements Comparable<ComparableListNode> {

        ListNode listNode;

        public ComparableListNode(ListNode listNode) {
            this.listNode = listNode;
        }

        @Override
        public int compareTo(ComparableListNode o) {
            return listNode.val - o.listNode.val;
        }
    }

    public ListNode priority(ListNode[] lists) {
        if (lists == null || lists.length <= 0) return null;
        PriorityQueue<ComparableListNode> queue = new PriorityQueue<>();
        for (ListNode list : lists) {
            if(list == null) continue;
            queue.offer(new ComparableListNode(list));
        }

        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        while(!queue.isEmpty()){
            ComparableListNode next = queue.poll();
            curr.next = next.listNode;
            curr = curr.next;
            if (next.listNode != null && next.listNode.next != null) queue.offer(new ComparableListNode(next.listNode.next));
        }
        return dummy.next;
    }
}
