package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author li.bowei
 */

@Slf4j
public class QueueImplStackV1 {

    private final Queue<Integer> queue;

    public QueueImplStackV1() {
        queue = new LinkedBlockingQueue<>();
    }

    public void push(int x){
        int n = queue.size();
        queue.offer(x);
        for (int i = 0; i < n; i++) {
            queue.offer(queue.poll());
        }
    }

    public int pop() {
        if(queue.isEmpty()){
            return 0;
        }
        return queue.poll();
    }

    public int top() {
        if(queue.isEmpty()){
            return 0;
        }
        return queue.poll();
    }

    public boolean empty() {
        return queue.isEmpty();
    }


    public static void main(String[] args) {
        QueueImplStackV1 queue = new QueueImplStackV1();
        for (int i = 0; i < 10; i++) {
            queue.push(i);
        }
        while(!queue.empty()){
            log.info("{}", queue.pop());
        }
    }
}
