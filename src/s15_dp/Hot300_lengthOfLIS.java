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
    public int lengthOfLIS_1(int[] nums) {
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

    /**
     * 贪心 + 二分
     * tails[k] 表示：长度为 k+1 的递增子序列的「最小末尾元素」
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(n)
     */
    public int lengthOfLIS_2(int[] nums) {
        // tails 始终严格递增；有效部分是前 size 个
        int[] tails = new int[nums.length];
        int size = 0; // 当前 LIS 长度（tails 中有效元素个数）

        for (int num : nums) {
            // 在 tails[0..size-1] 中二分找第一个 >= num 的位置 left（lower_bound）
            int left = 0, right = size;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < num) {
                    left = mid + 1; // 比 num 小：答案在右半
                } else {
                    right = mid; // >= num：可能是答案，往左继续夹
                }
            }

            tails[left] = num;      // 替换/写入：让长度为 left+1 的序列末尾尽可能小
            if (left == size) size++; // num 比所有末尾都大：接长一档
        }

        return size; // size 即 LIS 长度
    }

    public static void main(String[] args) {
        Hot300_lengthOfLIS hot300_lengthOfLIS = new Hot300_lengthOfLIS();
        // [2,3,7,101] 或 [2,5,7,101] 等，最长递增子序列长度为 4
        System.out.println(hot300_lengthOfLIS.lengthOfLIS_1(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        System.out.println(hot300_lengthOfLIS.lengthOfLIS_2(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
    }
}
