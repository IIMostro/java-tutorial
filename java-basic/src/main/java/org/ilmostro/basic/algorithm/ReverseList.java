package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * 反转链表
 *
 * @author li.bowei
 */
@Slf4j
public class ReverseList {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        ListNode listNode = new ListNode(arr);
        ListNode recursion = new ReverseList().reverseListV1(listNode);
        System.out.println(recursion);
    }

    /**
     * 使用while循环的方式来反转，这个地方需要注意的就是要多保存信息
     * <p>
     * curr 当前的指针
     * per 需要反转的指针的前一个元素
     *
     * @param head 反转的链表
     * @return 反转之后的链表
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     * 递归的反转
     *
     * 这个地方虽然看上去是递归，但是实质上就是先遍历到最后一个元素，并且是保存了pre的
     * ret 相当于是 prev
     * 参数head 就是 current.next
     *
     * @param head 需要翻转的链表
     * @return 反转之后的链表
     */
    public ListNode reverseListV1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ret = reverseListV1(head.next);
        log.info("head:{}, ret:{}", head, ret);
        head.next.next = head;
        head.next = null;
        return ret;
    }
}
