package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import org.ilmostro.basic.algorithm.ListNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/palindrome-linked-list/}
 */
public class PalindromeLinkedList {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(new int[]{1, 2, 2, 1});
        boolean palindrome = new PalindromeLinkedList().isPalindrome(listNode);
        System.out.println(palindrome);
    }

    public boolean isPalindrome(ListNode head) {
        List<Integer> compare = new ArrayList<>();
        while (head != null) {
            compare.add(head.val);
            head = head.next;
        }
        for (int i = 0, j = compare.size() - 1; j > i; i++, j--) {
            if(!compare.get(i).equals(compare.get(j))){
                return false;
            }
        }
        return true;
    }

    public boolean dequeue(ListNode head){
        if(head == null || head.next == null) return true;
        Deque<Integer> deque = new LinkedList<>();
        while(head!=null){
            deque.offerLast(head.val);
            head = head.next;
        }
        while(!deque.isEmpty()){
            Integer left = deque.pollFirst();
            if(deque.isEmpty()) return true;
            Integer right = deque.pollLast();
            if(!left.equals(right)) return false;
        }
        return true;
    }
}
