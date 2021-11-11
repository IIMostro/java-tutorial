package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * @author li.bowei
 */
@Slf4j
public class StackImplQueue {

    private final Stack<Integer> first;
    private final Stack<Integer> second;

    public StackImplQueue() {
        this.first = new Stack<>();
        this.second = new Stack<>();
    }

    public void appendTail(int value) {
        first.push(value);
    }

    public int deleteHead() {
        if(!second.isEmpty()){
            return second.pop();
        }
        if(first.isEmpty()){
            return -1;
        }
        while(!first.isEmpty()){
            second.push(first.pop());
        }
        return second.pop();
    }


    public static void main(String[] args) {
        StackImplQueue stack = new StackImplQueue();
        for (int i = 0; i <10; i++) {
            stack.appendTail(i);
        }
        for (int i = 0; i < 10; i++) {
            log.info("{}", stack.deleteHead());
        }
    }
}
