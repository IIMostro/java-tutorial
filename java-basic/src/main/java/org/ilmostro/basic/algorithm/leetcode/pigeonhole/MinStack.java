package org.ilmostro.basic.algorithm.leetcode.pigeonhole;

import java.util.Stack;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/}
 */
public class MinStack {

    Stack<Integer> stack;
    Stack<Integer> min;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        min = new Stack<>();
        stack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        if(x <= min.peek() || min.isEmpty()){
            min.push(x);
        }else{
            min.push(min.peek());
        }
    }

    public void pop() {
        stack.pop();
        min.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int min() {
        return min.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(0);
        minStack.push(1);
        minStack.push(0);
        minStack.min();
        minStack.pop();
        minStack.min();
    }
}
