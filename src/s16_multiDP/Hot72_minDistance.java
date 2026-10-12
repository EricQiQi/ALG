package s16_multiDP;

/**
 * 72. 编辑距离
 *
 * 双串二维 DP：dp[i][j] = word1 前 i 个字符转换成 word2 前 j 个字符所需的最少操作数。
 * 字符相等看左上（0 代价配对）；不等时取「插入/删除/替换」三种操作的最优 +1。
 * 时间 O(mn)，空间 O(mn)。
 */
public class Hot72_minDistance {

    /**
     * @param word1 源字符串
     * @param word2 目标字符串
     * @return 把 word1 转换成 word2 的最少操作步数（插入/删除/替换一个字符均算一步）
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        // dp 表多开一行一列，第 0 行/列表示"空前缀"，字符比较时下标回退一位
        int[][] dp = new int[m + 1][n + 1];
        // 边界：word2 为空只能全删除，dp[i][0]=i；word1 为空只能全插入，dp[0][j]=j
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 当前字符天然配对，不花操作，直接继承左上角
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 三种操作取最优再 +1：
                    // dp[i][j-1]   插入 word2[j-1]（消掉目标多出的字符）
                    // dp[i-1][j]   删除 word1[i-1]（消掉源多出的字符）
                    // dp[i-1][j-1] 替换 word1[i-1] 为 word2[j-1]（两字符强行配对）
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        Hot72_minDistance hot72_minDistance = new Hot72_minDistance();
        // horse -> ros：替换 h->r、删除 r、删除 e，共 3 步
        int i = hot72_minDistance.minDistance("horse", "ros");
        System.out.println(i); // 3
    }
}
