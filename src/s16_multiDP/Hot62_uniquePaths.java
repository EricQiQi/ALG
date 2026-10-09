package s16_multiDP;

/**
 * 62. 不同路径
 */
public class Hot62_uniquePaths {

    /**
     * 多维动态规划解法
     * 时间复杂度：O(m*n)
     * 空间复杂度：O(m*n)
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i=0; i<m; i++) dp[i][0] = 1;
        for(int j=0; j<n; j++) dp[0][j] = 1;

        for(int i=1; i<m; i++){
            for(int j=1; j<n; j++){
                // 定义状态： dp[i][j] = 从起点 (0,0) 走到 (i,j) 的路径数量。
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    public static void main(String[] args) {
        Hot62_uniquePaths uniquePaths = new Hot62_uniquePaths();
        System.out.println(uniquePaths.uniquePaths(3, 7));  // 输出 28
        System.out.println(uniquePaths.uniquePaths(3, 2));  // 输出 3
    }
}
