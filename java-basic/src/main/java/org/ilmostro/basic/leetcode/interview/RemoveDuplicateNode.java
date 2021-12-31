package org.ilmostro.basic.leetcode.interview;

import org.ilmostro.basic.algorithm.ListNode;
import org.ilmostro.basic.annotation.SolveError;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
 *
 * 示例1:
 *
 *  输入：[1, 2, 3, 3, 2, 1]
 *  输出：[1, 2, 3]
 * 示例2:
 *
 *  输入：[1, 1, 1, 1, 2]
 *  输出：[1, 2]
 * 提示：
 *
 * 链表长度在[0, 20000]范围内。
 * 链表元素在[0, 20000]范围内。
 * 进阶：
 *
 * 如果不得使用临时缓冲区，该怎么解决？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-duplicate-node-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class RemoveDuplicateNode {

    public static void main(String[] args) {
        ListNode node = new ListNode(new int[]{1, 1, 1, 1, 1, 2});
        ListNode listNode = new RemoveDuplicateNode().removeDuplicateNodes(node);
        System.out.println(listNode);
    }

    public ListNode removeDuplicateNodes(ListNode head) {
        Set<Integer> set = new HashSet<>();
        recursion(head, set);
        return head;
    }

    public ListNode dummyDelete(ListNode head){
        if(head == null){
            return null;
        }
        ListNode dummy = new ListNode(0, head);
        Set<Integer> set = new HashSet<>();
        while(dummy.next != null){
            ListNode next = dummy.next;
            if(set.contains(next.val)){
                dummy.next = dummy.next.next;
            }else{
                set.add(next.val);
                dummy = dummy.next;
            }
        }
        return head;
    }

    @SolveError
    public ListNode recursion(ListNode head, Set<Integer> set){
        if (head == null) {
            return null;
        }
        head.next = recursion(head.next, set);
        return set.add(head.val) ? head.next : head;
    }
}
