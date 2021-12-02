package org.ilmostro.basic.leetcode;

import org.ilmostro.basic.array.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 2021年12月2日10:04:06 名次
 * <p>
 * 给你一个长度为 n 的整数数组 score ，其中 score[i] 是第 i 位运动员在比赛中的得分。所有得分都 互不相同 。
 * <p>
 * 运动员将根据得分 决定名次 ，其中名次第 1 的运动员得分最高，名次第 2 的运动员得分第 2 高，依此类推。运动员的名次决定了他们的获奖情况：
 * <p>
 * 名次第 1 的运动员获金牌 "Gold Medal" 。
 * 名次第 2 的运动员获银牌 "Silver Medal" 。
 * 名次第 3 的运动员获铜牌 "Bronze Medal" 。
 * 从名次第 4 到第 n 的运动员，只能获得他们的名次编号（即，名次第 x 的运动员获得编号 "x"）。
 * 使用长度为 n 的数组 answer 返回获奖，其中 answer[i] 是第 i 位运动员的获奖情况。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/relative-ranks
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 * @link {https://leetcode-cn.com/problems/relative-ranks/}
 */
public class Solution {


    public static void main(String[] args) {
        int[] scores = new int[]{1, 2, 3, 44, 5, 6, 7, 8, 9};
        String[] relativeRanks = new Solution().findRelativeRanks(scores);
        ArrayUtils.print(relativeRanks);
    }


    public String[] findRelativeRanks(int[] score) {
        Map<Integer, Integer> userScoreMaps = new HashMap<>();
        for (int i = 0; i < score.length; i++) {
            userScoreMaps.put(score[i], i);
        }
        Arrays.sort(score);
        String[] result = new String[score.length];
        for (int i = 0; i < score.length; i++) {
            Integer index = userScoreMaps.get(score[i]);
            if (i == score.length - 1) {
                result[index] = "Gold Medal";
            } else if (i == score.length - 2) {
                result[index] = "Silver Medal";
            } else if (i == score.length - 3) {
                result[index] = "Bronze Medal";
            } else {
                result[index] = String.valueOf(score.length - i);
            }
        }
        return result;
    }
}
