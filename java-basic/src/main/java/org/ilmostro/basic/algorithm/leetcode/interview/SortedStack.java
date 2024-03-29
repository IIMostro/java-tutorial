package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.Stack;

/**
 *
 * 栈排序。 编写程序，对栈进行排序使最小元素位于栈顶。最多只能使用一个其他的临时栈存放数据，但不得将元素复制到别的数据结构（如数组）中。该栈支持如下操作：push、pop、peek 和 isEmpty。当栈为空时，peek 返回 -1。
 *
 * 示例1:
 *
 *  输入：
 * ["SortedStack", "push", "push", "peek", "pop", "peek"]
 * [[], [1], [2], [], [], []]
 *  输出：
 * [null,null,null,1,null,2]
 * 示例2:
 *
 *  输入：
 * ["SortedStack", "pop", "pop", "push", "pop", "isEmpty"]
 * [[], [], [], [1], [], []]
 *  输出：
 * [null,null,null,null,null,true]
 * 说明:
 *
 * 栈中的元素数目在[0, 5000]范围内。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-of-stacks-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class SortedStack {

    public static void main(String[] args) {
        SortedStack stack = new SortedStack();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        while (!stack.isEmpty()){
            System.out.println(stack.peek());
            stack.pop();
        }
    }

    Stack<Integer> stack;

    public SortedStack() {
        stack = new Stack<>() ;
    }

    public void push(int val) {
        // 这一条就是把最大的压到了最底部
        if (stack.isEmpty() || stack.peek() >= val) {
            stack.push(val);
        }else{
            Integer temp = stack.pop();
            push(val);
            stack.push(temp);
        }
    }

    public void pop() {
        if(stack.isEmpty()){
            return;
        }
        stack.pop();
    }

    public int peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
