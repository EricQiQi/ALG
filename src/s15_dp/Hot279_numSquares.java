package s15_dp;

/**
 * 279. 完全平方数
 * <p>
 * 给你一个整数 n，返回和为 n 的完全平方数的最少数量。
 * 完全平方数：可以写成某个整数平方的数，如 1、4、9、16……
 */
public class Hot279_numSquares {

    /**
     * 动态规划（完全背包模型）
     * dp[i] 表示：和为 i 所需完全平方数的最少数量
     * 时间复杂度：O(n * √n)
     * 空间复杂度：O(n)
     */
    public int numSquares(int n) {
        // dp[i]：组成 i 所需的最少完全平方数个数
        int[] dp = new int[n + 1];

        // 最坏情况：i 全部用 1 来凑（1 是完全平方数），需要 i 个，作为初始上界
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
        }

        // 从小到大递推每个目标和 i
        for (int i = 1; i <= n; i++) {
            // 枚举不超过 i 的每个完全平方数 j*j，尝试用它作为「最后加的一项」
            for (int j = 1; j * j <= i; j++) {
                // 转移：凑出 i - j*j 的最少个数 + 当前这一个平方数
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Hot279_numSquares hot279_numSquares = new Hot279_numSquares();
        // 12 = 4 + 4 + 4，最少 3 个完全平方数
        System.out.println(hot279_numSquares.numSquares(12));
    }
}
