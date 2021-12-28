package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * <p>
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: prices = [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 * 示例 2:
 * <p>
 * 输入: prices = [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * 示例 3:
 * <p>
 * 输入: prices = [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= prices.length <= 3 * 104
 * 0 <= prices[i] <= 104
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * dp[i][0]=max{dp[i−1][0],dp[i−1][1]+prices[i]}
 * dp[i][1]=max{dp[i−1][1],dp[i−1][0]−prices[i]}
 *
 * @author li.bowei
 */
public class Solution15 {

    public static void main(String[] args) {
        int[] nums = {7, 1, 5, 3, 6, 4};
        int i = new Solution15().maxProfit(nums);
        System.out.println(i);
    }

    public int maxProfit(int[] prices) {
        return originDynamic(prices);
    }

    public int originDynamic(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            //第 i 天交易完后手里没有股票的最大利润
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //表示第i天交易完后手里持有一支股票的最大利润
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }


    public int dynamic(int[] prices) {
        int r = 0, p = -prices[0];
        for (int i = 1; i < prices.length; ++i) {
            int newR = Math.max(r, p + prices[i]);
            int newP = Math.max(p, r - prices[i]);
            r = newR;
            p = newP;
        }
        return r;
    }

    public int dynamicV1(int[] prices){
        int sell = 0, buy = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            sell = Math.max(buy + prices[i], sell);
            buy = Math.max(buy, sell - prices[i]);
        }
        return sell;
    }

    public int greed(int[] prices) {
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max += Math.max(0, prices[i] - prices[i - 1]);
        }
        return max;
    }
}
