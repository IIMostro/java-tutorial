package org.ilmostro.basic.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author li.bowei
 */
@Slf4j
public class QueueImplStack {

    private final Queue<Integer> master;
    private final Queue<Integer> salver;


    public QueueImplStack() {
        master = new LinkedBlockingQueue<>();
        salver = new LinkedBlockingQueue<>();
    }

    public void push(Integer element){
        while(!master.isEmpty()){
            salver.offer(master.poll());
        }
        master.offer(element);
        while(!salver.isEmpty()){
            master.offer(salver.poll());
        }
    }

    public Integer pop(){
        return master.poll();
    }

    public Integer top(){
        return master.peek();
    }


    public boolean empty(){
        return master.isEmpty();
    }

    public static void main(String[] args) {
        QueueImplStack queue = new QueueImplStack();
        for (int i = 0; i < 10; i++) {
            queue.push(i);
        }

        while(!queue.empty()){
            log.info("{}", queue.pop());
        }
    }
}
