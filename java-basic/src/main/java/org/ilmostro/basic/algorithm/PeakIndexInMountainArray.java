package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 * @link https://leetcode-cn.com/problems/peak-index-in-a-mountain-array/
 */
public class PeakIndexInMountainArray {

    public static int peakIndexInMountainArray(int[] arr) {
        int j = arr.length - 1;
        int index = 0;
        int max = 0;
        int maxIndex = 0;
        while(index <= j){
            if (max < arr[index]) {
                max = arr[index];
                maxIndex = index;
            }
            if (max < arr[j]) {
                max = arr[j];
                maxIndex = j;
            }
            j--;
            index++;
        }
        return maxIndex;
    }

    public static void main(String[] args) {
        int[] arr = {24,69,100,99,79,78,67,36,26,19};
        int maxIndex = peakIndexInMountainArray(arr);
        System.out.println(maxIndex);
    }
}
