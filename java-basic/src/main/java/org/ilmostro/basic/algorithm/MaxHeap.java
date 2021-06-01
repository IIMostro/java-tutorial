package org.ilmostro.basic.algorithm;

import java.util.Objects;

/**
 * @author li.bowei
 */
public class MaxHeap {

    //数据，数组开始从1开始，但是对用户屏蔽
    protected int[] data;
    //总数
    protected int count;
    //初始化大小
    protected int capacity;

    public MaxHeap(int capacity) {
        this.data = new int[capacity + 1];
        this.capacity = capacity;
    }

    public MaxHeap(int[] arr){
        data = new int[arr.length + 1];
        capacity = arr.length;
        this.count = arr.length;
        System.arraycopy(arr, 0, data, 1, arr.length);
        for(int i = count / 2; i >= 1; i --){
            shiftDown(i);
        }
    }


    protected synchronized void resize(){
        int[] temp = new int[capacity + capacity / 2];
        System.arraycopy(data, 0, temp, 0, data.length);
        this.data = temp;
        this.capacity = capacity + capacity /2;
    }

    public int size(){
        return count;
    }

    public boolean isEmpty(){
        return count == 0;
    }

    public void push(int obj){
        if(count >= capacity){
            resize();
        }
        data[++count] = obj;
        shiftUp(count);
    }

    public int popMax(){
        if(Objects.isNull(data) || count <= 0){
            throw new RuntimeException();
        }
        int result = data[1];
        ArrayTestHelper.swap(data, 1, count--);
        shiftDown(1);
        return result;
    }


    private void shiftUp(int k){
        while(k > 1 && data[k/2] < data[k]){
            ArrayTestHelper.swap(data, k/2, k);
            k /= 2;
        }
    }

    /**
     *  向下切换元素
     *
     * @param k 节点的id
     */
    private void shiftDown(int k){
        //有左边的孩子
        while(2 * k <= count){
            int j = k * 2;

            //有右孩子， 找到那个孩子比较大
            if(j + 1 <= count && data[j + 1] > data[j]){
                j += 1;
            }

            //比较当前位置与孩子节点的大小
            if(data[k]> data[j]){
                break;
            }

            ArrayTestHelper.swap(data, k ,j);
            k = j;
        }
    }
}
