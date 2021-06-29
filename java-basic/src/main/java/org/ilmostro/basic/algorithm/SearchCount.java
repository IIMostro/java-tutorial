package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class SearchCount {

    public int search(int[] nums, int target) {
        return lineSearch(nums, target) - lineSearch(nums, target - 1);
    }

    private int lineSearch(int[] nums, int target){
        int i = 0;
        int j = nums.length - 1;
        while(i <= j){
            int mid = (i + j) / 2;
            if (nums[mid] <= target){
                i = mid + 1;
            }else {
                j = mid - 1;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        SearchCount searchCount = new SearchCount();

        int[] ints = new int[]{0,1,2,2,3,4,5,6};
        int i = searchCount.search(ints, 2);
        System.out.println(i);
    }
}
