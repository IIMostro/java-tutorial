package org.ilmostro.basic.leetcode.interview;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author li.bowei
 */
public class MovingAverage {

    public static void main(String[] args) {
        MovingAverage average = new MovingAverage(3);
        average.next(1);
        average.next(10);
        average.next(3);
        average.next(5);
    }

    final int size;
    final Queue<Integer> queue;
    int sum;

    /**
     * Initialize your data structure here.
     */
    public MovingAverage(int size) {
        this.size = size;
        this.queue = new LinkedList<>();
        this.sum = 0;
    }

    public double next(int val) {
        if(queue.size() >= size && !queue.isEmpty()){
            sum -= queue.poll();
        }
        sum+= val;
        queue.offer(val);
        return Math.round(((double)sum / queue.size()) * 100000) * 0.00001d;
    }
}
