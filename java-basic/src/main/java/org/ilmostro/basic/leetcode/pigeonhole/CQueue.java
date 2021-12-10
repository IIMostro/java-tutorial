package org.ilmostro.basic.leetcode.pigeonhole;

import java.util.Stack;

/**
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/}
 */
public class CQueue {

    private final Stack<Integer> first;
    private final Stack<Integer> second;

    public CQueue() {
        first = new Stack<>();
        second = new Stack<>();
    }

    public void appendTail(int value) {
        first.push(value);
    }

    public int deleteHead() {
        if(second.isEmpty() && first.isEmpty()){
            return -1;
        }
        if(!second.isEmpty()){
            return second.pop();
        }
        while(!first.empty()){
            second.push(first.pop());
        }
        return second.pop();
    }


    public static void main(String[] args) {

        CQueue queue = new CQueue();
        for (int i = 0; i < 10; i++) {
            queue.appendTail(i);
        }
        for (int i = 0; i < 5; i++) {
            queue.appendTail(i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.deleteHead());
        }
    }
}
