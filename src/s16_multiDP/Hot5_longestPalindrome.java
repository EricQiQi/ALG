package s16_multiDP;

/**
 * 5. 最长回文子串
 */
public class Hot5_longestPalindrome {

    /**
     * 方法1：中心扩展法
     * <p>
     * 时间复杂度：O(n²)，每个中心扩展最多 O(n)，共 2n 个中心
     * 空间复杂度：O(1)
     */
    public String longestPalindrome_1(String s) {
        // 边界处理：空串或单字符直接返回
        if (s == null || s.length() < 2) {
            return s;
        }

        int start = 0;   // 最长回文子串的起始位置
        int maxLen = 1;  // 最长回文子串的长度

        for (int i = 0; i < s.length(); i++) {
            // 情况1：以 s[i] 为中心（奇数长度回文，如 "aba"）
            int len1 = expand(s, i, i);

            // 情况2：以 s[i] 和 s[i+1] 之间为中心（偶数长度回文，如 "abba"）
            int len2 = expand(s, i, i + 1);

            // 取两种情况的较大值
            int len = Math.max(len1, len2);

            // 如果找到更长的回文，更新记录
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;  // 计算起始位置
            }
        }

        return s.substring(start, start + maxLen);
    }

    /**
     * 从中心 (left, right) 向两边扩展，返回回文串长度
     *
     * @param s     字符串
     * @param left  左指针
     * @param right 右指针
     * @return 以 (left, right) 为中心的最长回文长度
     */
    private int expand(String s, int left, int right) {
        // 当左右指针没越界，且字符相等时，继续扩展
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 循环结束时，left 和 right 已经多走了一步
        // 实际回文长度 = right - left - 1
        return right - left - 1;
    }

    /**
     * 方法2：动态规划
     * 时间复杂度：O(n²)
     * 空间复杂度：O(n²)
     */
    public String longestPalindrome_2(String s) {
        // 边界处理：空串或单字符直接返回
        if (s == null || s.length() < 2) {
            return s;
        }

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0;
        int maxLen = 1;

        // 初始化：单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // 按子串长度从小到大遍历
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;  // 子串结束位置

                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    // 长度 ≤ 3，两端相等就是回文
                    if (len <= 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 更新最长记录
                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    start = i;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    /**
     * 方法3：Manacher 算法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public String longestPalindrome_3(String s) {
        // 难 ... O(n)

        return s;
    }

    public static void main(String[] args) {
        Hot5_longestPalindrome sol = new Hot5_longestPalindrome();
        System.out.println(sol.longestPalindrome_1("babad"));   // "bab" 或 "aba"
        System.out.println(sol.longestPalindrome_1("cbbd"));    // "bb"
        System.out.println(sol.longestPalindrome_1("a"));       // "a"
        System.out.println(sol.longestPalindrome_1("ac"));      // "a" 或 "c"
        System.out.println(sol.longestPalindrome_1("aaaa"));    // "aaaa"
        System.out.println("");
        System.out.println(sol.longestPalindrome_2("babad"));   // "bab" 或 "aba"
        System.out.println(sol.longestPalindrome_2("cbbd"));    // "bb"
        System.out.println(sol.longestPalindrome_2("a"));       // "a"
        System.out.println(sol.longestPalindrome_2("ac"));      // "a" 或 "c"
        System.out.println(sol.longestPalindrome_2("aaaa"));    // "aaaa"
    }

}
