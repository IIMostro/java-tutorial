package org.ilmostro.basic.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li.bowei
 */
public class Array {

    public static void main(String[] args) {
        ListNode node = new ListNode(4);
        ListNode node1 = new ListNode(5);
        node1.next = new ListNode(6);
        node.next = node1;

        int number = new Array().getNumber(node);
        System.out.println(number);
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            int y = target - x;
            if (map.containsKey(y)) {
                return new int[]{map.get(y), i};
            }
            map.put(x, i);
        }
        return null;
    }

    public int getNumber(ListNode node) {
        ListNode curr = node.next;
        int number = node.val;
        int bit = 1;
        while (curr != null) {
            number += (Math.pow(10, bit++)) * curr.val;
            curr = curr.next;
        }
        return number;
    }
}
