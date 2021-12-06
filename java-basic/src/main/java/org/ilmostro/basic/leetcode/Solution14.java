package org.ilmostro.basic.leetcode;

import org.ilmostro.basic.algorithm.ListNode;

/**
 *
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 *
 *
 * @link {https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/}
 * @author li.bowei
 */
public class Solution14 {

    public static void main(String[] args) {
        ListNode listNode = new ListNode();
        int[] ints = new Solution14().reversePrint(listNode);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public int[] reversePrint(ListNode head) {
        if(head == null){
            return new int[0];
        }
        return doReversePrint(head, 1);
    }

    public int[] doReversePrint(ListNode head, int dep){
        if(head.next == null){
            int [] ints = new int[dep];
            ints[0] = head.val;
            return ints;
        }
        int[] ints = doReversePrint(head.next, dep + 1);
        ints[ints.length - dep] = head.val;
        return ints;
    }
}
