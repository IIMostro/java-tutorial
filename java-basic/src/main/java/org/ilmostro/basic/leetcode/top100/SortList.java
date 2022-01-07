package org.ilmostro.basic.leetcode.top100;

import org.ilmostro.basic.algorithm.ListNode;

import java.util.*;

/**
 *
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 *
 *  
 *
 * 示例 1：
 *
 *
 * 输入：head = [4,2,1,3]
 * 输出：[1,2,3,4]
 * 示例 2：
 *
 *
 * 输入：head = [-1,5,3,4,0]
 * 输出：[-1,0,3,4,5]
 * 示例 3：
 *
 * 输入：head = []
 * 输出：[]
 *  
 *
 * 提示：
 *
 * 链表中节点的数目在范围 [0, 5 * 104] 内
 * -105 <= Node.val <= 105
 *  
 *
 * 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SortList {

    public ListNode sortList(ListNode head) {
        return simplicity(head);
    }

    public ListNode simplicity(ListNode head){
        List<Integer> temp = new LinkedList<>();
        while(head != null){
            temp.add(head.val);
            head = head.next;
        }
        Collections.sort(temp);
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        for (Integer integer : temp) {
            curr.next = new ListNode(integer);
            curr = curr.next;
        }
        return dummy.next;
    }
}
