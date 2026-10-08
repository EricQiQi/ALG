package s15_dp;

import java.util.Arrays;

/**
 * 322. 零钱兑换
 */
public class Hot322_coinChange {

    /**
     * 完全背包问题
     * 时间复杂度：O(Sn)，S 为金额 amount， n 为面额数
     * 空间复杂度：O(S)
     */
    public int coinChange(int[] coins, int amount) {
        // dp[j]：凑出金额 j 的最少硬币个数；amount+1 作为「凑不出」的哨兵上界
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0; // 金额 0 需要 0 枚

        for (int coin : coins) {                 // 外层物品
            for (int j = coin; j <= amount; j++) { // 内层背包，正序允许复用同一枚
                dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }

        // 仍是哨兵：凑不出，返回 -1
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        Hot322_coinChange hot322_coinChange = new Hot322_coinChange();
        System.out.println(hot322_coinChange.coinChange(new int[]{1, 2, 5}, 11));
    }
}
