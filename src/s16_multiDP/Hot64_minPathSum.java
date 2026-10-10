package s16_multiDP;

/**
 * 64. 最小路径和
 */
public class Hot64_minPathSum {

    /**
     * 方法1：动态规划
     * 时间复杂度：O(mn)
     * 空间复杂度：O(mn)
     */
    public int minPathSum_1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // dp[i][j] = 从起点 (0,0) 走到 (i,j) 的最小路径和
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        // 初始化第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // 初始化第一行
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // 其他位置
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 方法2：动态规划+空间优化
     * 时间复杂度：O(mn)
     * 空间复杂度：O(n)
     */
    public int minPathSum_2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0];

        for(int j=1; j<n; j++){
            dp[j] = dp[j-1] + grid[0][j];
        }
        for(int i=1; i<m; i++){
            dp[0] += grid[i][0];
            for(int j=1; j<n; j++){
                dp[j] = grid[i][j] + Math.min(dp[j-1], dp[j]);
            }
        }
        return dp[n-1];
    }

    public static void main(String[] args) {
        Hot64_minPathSum solution = new Hot64_minPathSum();
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        System.out.println(solution.minPathSum_1(grid));
        System.out.println(solution.minPathSum_2(grid));
    }

}
