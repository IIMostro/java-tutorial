package org.ilmostro.basic.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 你和你的朋友，两个人一起玩 Nim 游戏：
 * <p>
 * 桌子上有一堆石头。
 * 你们轮流进行自己的回合，你作为先手。
 * 每一回合，轮到的人拿掉 1 - 3 块石头。
 * 拿掉最后一块石头的人就是获胜者。
 * 假设你们每一步都是最优解。请编写一个函数，来判断你是否可以在给定石头数量为 n 的情况下赢得游戏。如果可以赢，返回 true；否则，返回 false 。
 * <p>
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/nim-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class Solution22 {

    public static void main(String[] args) {
        boolean b = new Solution22().canWinNim(1348820612);
        System.out.println(b);
    }

    public boolean canWinNimMod(int n) {
        return !(n % 4 == 0);
    }

    public boolean canWinNim(int n) {
        Map<Integer, Boolean> memory = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            memory.put(i, true);
        }
        return doCanWinNim(n, memory);
    }

    public boolean doCanWinNim(int n, Map<Integer, Boolean> memory) {
        if (memory.containsKey(n)) {
            return memory.get(n);
        }
        if (n <= 3) {
            return true;
        } else {
            boolean can = !(doCanWinNim(n - 1, memory) && doCanWinNim(n - 2, memory) && doCanWinNim(n - 3, memory));
            memory.put(n, can);
            return can;
        }
    }
}
