package org.ilmostro.basic.algorithm;

import java.util.Objects;

/**
 * @author li.bowei
 */
public class MoveZero {

    public static void main(String[] args) {
        int[] arr = new int[]{4,0, 1,1,1,3,0,12};
        moveZeroesV1(arr);
        ArrayTestHelper.printArray(arr, arr.length);
    }


    public static void moveZeroes(int[] nums) {
        if(Objects.isNull(nums) || nums.length <=0){
            return;
        }

        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != 0){
                nums[j++] = nums[i];
            }
        }

        for(int i = j ; i < nums.length; i++){
            nums[i] = 0;
        }
    }

    public static void moveZeroesV1(int[] nums){
        int k = 0;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] != 0){
                ArrayTestHelper.swap(nums, k++, i);
            }
        }
    }
}
