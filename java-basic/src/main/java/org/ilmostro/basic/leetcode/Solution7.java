package org.ilmostro.basic.leetcode;

import org.ilmostro.basic.algorithm.ListNode;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/merge-two-sorted-lists/}
 */
public class Solution7 {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(new int[]{1, 3, 5, 6, 7, 9});
        ListNode list2 = new ListNode(new int[]{2, 4, 6, 8, 10, 11});

        ListNode result = mergeTwoListsR(list1, list2);
        System.out.println(result);
        System.out.println(list1);
        System.out.println(list2);
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode result = new ListNode();
        ListNode currentResult = result;
        while (list1 != null && list2 != null) {
            if (list1.val >= list2.val) {
                currentResult.next = list2;
                list2 = list2.next;
            } else {
                currentResult.next = list1;
                list1 = list1.next;
            }
            currentResult = currentResult.next;
        }
        currentResult.next = list1 == null ? list2 : list1;
        return result.next;
    }

    /**
     * 递归写法
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoListsR(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l1.next = mergeTwoListsR(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoListsR(l1, l2.next);
            return l2;
        }
    }
}
