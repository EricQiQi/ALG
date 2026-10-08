package s15_dp;

import java.util.Arrays;

/**
 * 300. 最长上升子序列（最长递增子序列 LIS）
 * <p>
 * 给你一个整数数组 nums，找到其中最长严格递增子序列的长度。
 * 子序列：不要求连续，但要求相对顺序不变且严格递增。
 */
public class Hot300_lengthOfLIS {

    /**
     * 动态规划
     * dp[i] 表示：以 nums[i] 结尾的最长递增子序列长度
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n)
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n <= 1) return n; // 空数组或单元素，答案即长度本身

        // dp[i]：以 nums[i] 结尾的 LIS 长度
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // 每个元素自身就构成长度为 1 的子序列

        int res = 1; // 全局最长（LIS 不一定以最后一个元素结尾，需单独跟踪）
        for (int i = 1; i < n; i++) {
            // 枚举 i 之前的每个 j，看 nums[i] 能否接在 nums[j] 后面
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) { // 能接：长度 = dp[j] + 1，取最大
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]); // 打擂台更新全局最长
        }
        return res;
    }

    public static void main(String[] args) {
        Hot300_lengthOfLIS hot300_lengthOfLIS = new Hot300_lengthOfLIS();
        // [2,3,7,101] 或 [2,5,7,101] 等，最长递增子序列长度为 4
        System.out.println(hot300_lengthOfLIS.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
    }
}
