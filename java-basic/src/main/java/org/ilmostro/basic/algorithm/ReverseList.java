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
            //head: 3 -> NULL
            log.info("return head:[{}]", head);
            return head;
        }
        log.info("head:[{}], head.next:[{}]", head, head.next);
        //递归到最后一个元素
        // head: 2-> 3-> NULL
        // head.next: 3 -> NULL
        // first ret: 3 -> NULL
        ListNode ret = reverseListV1(head.next);
        log.info("ret:[{}], head.next.next:[{}], head:[{}]", ret, head.next.next, head);
        //ret = 2 -> 3 -> NULL
        //head = 2-> 3 -> NULL
        //这个地方赋值就是将3的下一个指向2 -> 3- > NULL after 3 -> 2 -> 3
        head.next.next = head;
        //这个地方赋值就是将 2-> 3
        head.next = null;
        log.info("after ret:[{}], head:[{}]", ret, head);
        return ret;
    }

    /**
     * 递归实现
     *
     * @param current 翻转之前的listNode
     * @param prev    前一个节点
     * @return 翻转之后的listNode
     */
    private ListNode recursion(ListNode current, ListNode prev) {
        if (current == null) {
            return prev;
        }
        ListNode next = current.next;
        current.next = prev;
        return recursion(next, current);
    }
}
