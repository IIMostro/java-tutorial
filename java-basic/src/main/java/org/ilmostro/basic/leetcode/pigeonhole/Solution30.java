package org.ilmostro.basic.leetcode.pigeonhole;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 *
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 * @link {https://leetcode-cn.com/problems/pascals-triangle/}
 * @author li.bowei
 */
public class Solution30 {

    public static void main(String[] args) {
        List<List<Integer>> generate = new Solution30().generate(5);
        for (List<Integer> integers : generate) {
            for (Integer integer : integers) {
                System.out.println(integer);
            }
        }
    }

    public List<List<Integer>> generate(int numRows) {
        if(numRows <=0){
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < numRows; ++i) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j <= i; ++j) {
                if(j == 0 || i == j){
                    list.add(1);
                }else{
                    list.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
                }
            }
            result.add(list);
        }
        return result;
    }
}
