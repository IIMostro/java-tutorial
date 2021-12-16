package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;

/**
 *
 * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
 *
 * 返回删除后的链表的头节点。
 *
 * 注意：此题对比原题有改动
 *
 * 示例 1:
 *
 * 输入: head = [4,5,1,9], val = 5
 * 输出: [4,1,9]
 * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
 * 示例 2:
 *
 * 输入: head = [4,5,1,9], val = 1
 * 输出: [4,5,9]
 * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
 *  
 *
 * 说明：
 *
 * 题目保证链表中节点的值互不相同
 * 若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution16 {

    public static void main(String[] args) {
        int[] nums = new int[]{-3, 5, -99};
        ListNode root = new ListNode(nums);
        ListNode listNode = new Solution16().deleteNode(root, -99);
        System.out.println(listNode);
    }

    public ListNode deleteNode(ListNode head, int val) {
        return recursive(head, val);
    }

    public ListNode normal(ListNode head, int val){
        ListNode dummy = new ListNode(0, head);
        ListNode curr = dummy;
        while(curr != null && curr.next != null){
            if(curr.next.val == val){
                curr.next = curr.next.next;
            }
            curr = curr.next;
        }
        return dummy.next;
    }

    public ListNode recursive(ListNode head, int val){
        if(head == null){
            return null;
        }
        ListNode res = deleteNode(head.next, val);
        if(head.val == val){
            System.out.println(head);
            return res;
        }else{
            head.next = res;
            return head;
        }
    }
}
