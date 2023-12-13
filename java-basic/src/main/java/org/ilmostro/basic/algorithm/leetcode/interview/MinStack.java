package org.ilmostro.basic.algorithm.leetcode.interview;

import java.util.Stack;

/**
 * @author li.bowei
 */
public class MinStack {

    final Stack<Integer> main;
    final Stack<Integer> min;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        main = new Stack<>();
        min = new Stack<>();
    }

    public void push(int x) {
        main.push(x);
        min.push(min.isEmpty() ? x : x > min.peek() ? min.peek() : x);
    }

    public void pop() {
        main.pop();
        min.pop();
    }

    public int top() {
        return main.peek();
    }

    public int getMin() {
        return min.peek();
    }
}
