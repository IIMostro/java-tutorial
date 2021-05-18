package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class IndexMaxHeap extends MaxHeap{

    private int[] indexes;

    public IndexMaxHeap(int capacity) {
        super(capacity);
        this.indexes = new int[capacity + 1];
    }

    public IndexMaxHeap(int[] arr) {
        super(arr);
        this.indexes = new int[arr.length + 1];
    }

    @Override
    protected synchronized void resize() {
        super.resize();
        int[] temp = new int[capacity + capacity / 2];
        System.arraycopy(indexes, 0, temp, 0, data.length);
        this.indexes = temp;
    }

    @Override
    public void push(int obj) {
        if(count >= capacity){
            resize();
        }

    }

    public void push(int index, int obj){

    }

    @Override
    public int popMax() {
        return super.popMax();
    }
}
