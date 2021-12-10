package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;

/**
 *
 * 编写一个函数，检查输入的链表是否是回文的。
 *
 *  
 *
 * 示例 1：
 *
 * 输入： 1->2
 * 输出： false
 * 示例 2：
 *
 * 输入： 1->2->2->1
 * 输出： true
 *  
 *
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-linked-list-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution6 {

    public static void main(String[] args) {
        int[] ints = {1,2,2,1};
        ListNode listNode = new ListNode(ints);
        ListNode reversal = new Solution6().recursion(listNode);
        System.out.println(reversal);
    }

    public boolean isPalindrome(ListNode head) {
        return false;
    }

    public ListNode recursion(ListNode head, ListNode reversal){

    }
}
