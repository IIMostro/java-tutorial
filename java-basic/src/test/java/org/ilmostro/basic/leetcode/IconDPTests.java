package org.ilmostro.basic.leetcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
public class IconDPTests {

    int getMinCounts(int k, int[] values) {
        int[] memo = new int[k + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0; // 初始化状态

        for (int v = 1; v <= k; v++) {
            int minCount = k + 1; // 模拟无穷大
            for (int i = 0; i < values.length; ++i) {
                int currentValue = values[i];
                // 如果当前面值大于硬币总额，那么跳过
                if (currentValue > v) { continue; }
                // 使用当前面值，得到剩余硬币总额
                int rest = v - currentValue;
                int restCount = memo[rest];
                // 如果返回-1，说明组合不可信，跳过
                if (restCount == -1) { continue; }
                // 保留最小总额
                int kCount = 1 + restCount;
                if (kCount < minCount) { minCount = kCount; }
            }
            // 如果是可用组合，记录结果
            if (minCount != k + 1) { memo[v] = minCount; }
        }

        return memo[k];
    }

    @Test
    void test_get_min_count_should_work(){
        int[] values = { 3, 5 }; // 硬币面值
        int total = 22; // 总值
        // 求得最小的硬币数量
        final var result = getMinCounts(total, values); // 输出答案
        log.info("result: {}", result);
    }

    private int getMinCountsV2(int k, int[] values) {
        int[] memo = new int[k + 1];
        Arrays.fill(memo, k + 1);
        memo[0] = 0;
        for (int v = 1; v <= k; v++) {
            for (int coin : values) {
                if (v < coin){
                    continue;
                }
                memo[v] = Math.min(memo[v], memo[v - coin] + 1);
            }
        }
        return memo[k] == k + 1 ? -1 : memo[k];
    }

    @Test
    void test_get_min_count_v2_should_work(){
        int[] values = { 3, 5 }; // 硬币面值
        int total = 22; // 总值
        final var result = getMinCountsV2(total, values);
        log.info("result: {}", result);
    }
}
