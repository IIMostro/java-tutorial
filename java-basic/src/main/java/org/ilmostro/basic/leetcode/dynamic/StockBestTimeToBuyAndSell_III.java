package org.ilmostro.basic.leetcode.dynamic;

/**
 * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
 * <p>
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * <p>
 * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 示例:
 * <p>
 * 输入: [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class StockBestTimeToBuyAndSell_III {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 0, 2};
        int i = new StockBestTimeToBuyAndSell_III().dynamic(nums);
        System.out.println(i);
    }

    public int maxProfit(int[] prices) {
        return dynamic(prices);
    }

    public int normal(int[] prices){
        int[][] dp = new int[prices.length][3];

        //0.不持股且当天没卖出,定义其最大收益dp[i][0];
        //1.持股,定义其最大收益dp[i][1]；
        //2.不持股且当天卖出了，定义其最大收益dp[i][2]；
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;
        for (int i = 1; i < prices.length; i++) {//从[1]...[n-1]
            // 持有股票： 1.昨天持有股票 2.本日买入（条件：昨天不持有，且不是卖出）
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]);
            //本日卖出 : (条件:昨天持有)
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            // 不持有，但也不是卖出 : 1.昨天卖出，不持有  2.昨天没卖出，但也不持有
            dp[i][2] = dp[i - 1][1] + prices[i];

        }
        return Math.max(dp[prices.length - 1][0], dp[prices.length - 1][2]);
    }

    public int dynamic(int[] prices) {
        int n = prices.length;
        int buy = -prices[0];
        int sell = 0;
        int freeze = 0;
        for (int i = 1; i < n; ++i) {
            //这个代表是冷冻期有收益 freeze - prices[i]
            int tempBuy = Math.max(buy, freeze - prices[i]);
            int tempSell = buy + prices[i];
            int tempFreeze = Math.max(sell, freeze);

            buy = tempBuy;
            sell = tempSell;
            freeze = tempFreeze;
        }
        return Math.max(sell, freeze);
    }
}
