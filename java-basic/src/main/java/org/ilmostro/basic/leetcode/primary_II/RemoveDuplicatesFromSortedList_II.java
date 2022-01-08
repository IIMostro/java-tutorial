package org.ilmostro.basic.leetcode.primary_II;

import org.ilmostro.basic.algorithm.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中 没有重复出现 的数字。
 *
 * 返回同样按升序排列的结果链表。
 *
 *  
 *
 * 示例 1：
 *
 *
 * 输入：head = [1,2,3,3,4,4,5]
 * 输出：[1,2,5]
 * 示例 2：
 *
 *
 * 输入：head = [1,1,1,2,3]
 * 输出：[2,3]
 *  
 *
 * 提示：
 *
 * 链表中节点数目在范围 [0, 300] 内
 * -100 <= Node.val <= 100
 * 题目数据保证链表已经按升序排列
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class RemoveDuplicatesFromSortedList_II {

    public static void main(String[] args) {
        ListNode root = new ListNode(new int[]{1,1,1,2,3});
        ListNode listNode = new RemoveDuplicatesFromSortedList_II().deleteDuplicates(root);
        System.out.println(listNode);
    }

    public ListNode deleteDuplicates(ListNode head) {
        return normal(head);
    }

    public ListNode normal(ListNode head){
        if(head == null) return null;
        Map<Integer, Integer> count = new HashMap<>();
        ListNode curr = head;
        while(curr != null) {
            count.merge(curr.val, 1, Integer::sum);
            curr = curr.next;
        }
        ListNode dummy = new ListNode(-1, head);
        curr = dummy;
        while(curr.next != null){
            if (count.getOrDefault(curr.next.val, 0) > 1) {
                curr.next = curr.next.next;
            }else{
                curr = curr.next;
            }
        }
        return dummy.next;
    }

    public ListNode optimize(ListNode head){
        if(head == null) return null;
        ListNode dummy = new ListNode(-1, head);
        ListNode curr = dummy;
        while(curr.next != null && curr.next.next != null){
            if(curr.next.val == curr.next.next.val){
                int x = curr.next.val;
                while (curr.next != null && curr.next.val == x) {
                    curr.next = curr.next.next;
                }
            }else{
                curr = curr.next;
            }
        }
        return dummy.next;
    }
}
