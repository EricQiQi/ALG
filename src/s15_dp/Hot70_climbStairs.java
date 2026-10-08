package s15_dp;

/**
 * 70. 爬楼梯
 */
public class Hot70_climbStairs {

    /**
     * 方法一：动态规划
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int climbStairs_1(int n) {
        if (n <= 2) return n;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[1] + dp[2];
        }
        return dp[n];
    }

    /**
     * 方法二：滚动变量法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int climbStairs_2(int n) {
        if (n <= 2) return n;
        int prev2 = 1;
        int prev1 = 2;

        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        Hot70_climbStairs hot70_climbStairs = new Hot70_climbStairs();
        System.out.println(hot70_climbStairs.climbStairs_1(4));
        System.out.println(hot70_climbStairs.climbStairs_2(4));
    }
}
