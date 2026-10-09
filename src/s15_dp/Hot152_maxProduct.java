package s15_dp;

/**
 * 152. 乘积最大子数组
 * 求整数数组中乘积最大的连续子数组（子数组最少包含一个元素）。
 * 核心思路：与最大子数组和（Hot53）类似，但由于负数的存在，
 * 最小乘积遇到负数会反转成最大乘积，因此需要同时维护"最大/最小"两个状态。
 */
public class Hot152_maxProduct {

    /**
     * 暴力解法：枚举所有以 i 开头的子数组，边累乘边更新答案
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     *
     * @param nums 输入数组
     * @return 乘积最大的连续子数组的乘积
     */
    public int maxProduct_1(int[] nums) {
        int n = nums.length;
        int res = nums[0];

        for (int i = 0; i < n; i++) {        // 枚举起始位置 i
            int product = 1;                 // 累乘器，每换一个起点重置为 1
            for (int j = i; j < n; j++) {    // 终点 j 从 i 向右扩展，乘积逐步累乘
                product *= nums[j];
                res = Math.max(res, product);
            }
        }
        return res;
    }

    /**
     * 动态规划：滚动变量代替 dp 数组
     * 状态定义：maxDp/minDp 表示「以当前位置结尾」的连续子数组的最大/最小乘积
     * 为什么需要两个状态：nums[i] 为负时，上一步的 minDp（负数）乘以它会反转为最大值，
     *                  因此转移时必须同时参考上一步的最大值与最小值。
     * 转移方程：
     *   maxDp = max(num, maxDp * num, minDp * num)   // num 单独起一段 / 接上一步最大 / 接上一步最小
     *   minDp = min(num, maxDp * num, minDp * num)
     * 答案：所有位置 maxDp 的全局最大值
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param nums 输入数组
     * @return 乘积最大的连续子数组的乘积
     */
    public int maxProduct_2(int[] nums) {
        int n = nums.length;
        int maxDp = nums[0];  // 以当前位置结尾的最大乘积，初始化为第一个元素
        int minDp = nums[0];  // 以当前位置结尾的最小乘积，初始化为第一个元素
        int res = nums[0];    // 全局最大乘积（最终答案）

        for (int i = 1; i < n; i++) {
            int num = nums[i];
            // 先暂存上一步状态与当前数的乘积，避免 maxDp 更新后污染 minDp 的计算
            int tempMax = maxDp * num;
            int tempMin = minDp * num;
            // 三个候选：num 自身（断开重开一段）、上一步最大乘积 × num、上一步最小乘积 × num
            maxDp = Math.max(num, Math.max(tempMax, tempMin));
            minDp = Math.min(num, Math.min(tempMax, tempMin));
            // 以每个位置结尾的最大乘积中取全局最大
            res = Math.max(res, maxDp);
        }
        return res;
    }

    public static void main(String[] args) {
        Hot152_maxProduct solution = new Hot152_maxProduct();
        int[] nums = {2, 3, -2, 4};  // 最大乘积子数组为 [2,3]，乘积为 6（若带上 -2 会变小）
        int result = solution.maxProduct_1(nums);
        System.out.println(result); // 输出: 6

        result = solution.maxProduct_2(nums);
        System.out.println(result); // 输出: 6

    }
}
