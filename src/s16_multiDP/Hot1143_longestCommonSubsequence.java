package s16_multiDP;

/**
 * 1143. 最长公共子序列
 * <p>
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。如果不存在公共子序列，则返回 0 。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后得到的。
 * 例如，"ace" 是 "abcde" 的子序列，因为删除 "b" 和 "d" 后，"abcde" 变为 "ace" 。
 * 一个字符串的 子序列 的长度是它的长度。
 * <p>
 * 如果两个字符串没有公共子序列，则返回 0 。
 */
public class Hot1143_longestCommonSubsequence {

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m+1][n+1];

        for(int i=1; i<=m; i++){
            for(int j=1; j<=n; j++){
                if (text1.charAt(i-1) == text2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        Hot1143_longestCommonSubsequence sol = new Hot1143_longestCommonSubsequence();
        System.out.println(sol.longestCommonSubsequence("abcde", "ace"));   // 3
        System.out.println(sol.longestCommonSubsequence("abc", "abc"));     // 3
        System.out.println(sol.longestCommonSubsequence("abc", "def"));     // 0
    }
}
