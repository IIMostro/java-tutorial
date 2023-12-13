package org.ilmostro.basic.security.arithmetic;

/**
 * @author li.bowei
 */
public class SingleNumber {

    public static void main(String[] args) {
//        int[] nums = new int[]{3,2,2,3};
//        int len = removeElement(nums, 2);
//        for (int i = 0; i < len; i++) {
//            System.out.print(nums[i]);
//        }
//        System.out.println(Character.getNumericValue('Z'));
//        System.out.println(titleToNumber("FXSHRXW"));
//        int count = 16;
//        for(int i = 0; i < 10; i++){
//            count = count << 1;
//            System.out.println(count);
//        }
        int[] arr = new int[]{-1, 0, 3, 5, 9, 12};
        System.out.println(search(arr, 12));
    }

    private static int titleToNumber(String title) {
        char[] charArray = title.toCharArray();
        int res = 0;
        for (char c : charArray) {
            res = res * 26 + (c - 'A' + 1);
        }
        return res;
    }

    public static int removeElement(int[] nums, int val) {
        int targetIndex = nums.length - 1;
        int index = 0;
        while (index <= targetIndex) {
            if (nums[index] == val) {
                if (nums[targetIndex] == val) {
                    targetIndex--;
                } else {
                    int temp = nums[index];
                    nums[index] = nums[targetIndex];
                    nums[targetIndex] = temp;
                    targetIndex--;
                    index++;
                }
            } else {
                index++;
            }
        }
        return index;
    }


    public static int search(int[] nums, int target) {
        return doBinarySearch(nums, target, 0, nums.length - 1);
    }

    public static int doBinarySearch(int[] arr, int target, int l, int r) {
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return -1;
    }
}
