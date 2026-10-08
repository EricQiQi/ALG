package s15_dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 139. 单词拆分
 */
public class Hot139_wordBreak {

    /**
     * dp[i]：前 i 个字符能否被拆分
     * dp[i] = dp[j] && s[j..i-1] 在字典
     * dp[0] = true 空串视为可拆分
     *
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n)
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict); // 字典转 Set，contains 提速
        int n = s.length();
        boolean[] dp = new boolean[n + 1]; // dp[i]：前 i 个字符能否被拆分
        dp[0] = true; // 空串视为可拆分

        for (int i = 1; i <= n; i++) {           // 外层背包：前 i 个字符
            for (int j = 0; j < i; j++) {        // 内层枚举分割点
                // 前 j 个可拆 且 s[j..i-1] 在字典 → 前 i 个可拆
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // 找到一个可行分割即可
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        Hot139_wordBreak hot139_wordBreak = new Hot139_wordBreak();
        System.out.println(hot139_wordBreak.wordBreak("leetcode", Arrays.asList("leet", "code")));
    }
}
