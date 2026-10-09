package s15_dp;


/**
 * 416. 分割等和子集
 * 判断数组能否分成两个子集，使两个子集的元素和相等。
 * 核心思路：转化为 0/1 背包可行性问题——能否从数组中选出若干个数，恰好装满容量为 sum/2 的背包。
 */
public class Hot416_canPartition {

    /**
     * 0/1 背包（可行性问题，一维滚动数组）
     * 状态定义：dp[i] 表示能否从已遍历的数字中选出若干个，使和恰好为 i
     * 转移方程：dp[i] = dp[i] || dp[i-num]（不选 num 维持原状 / 选 num 看 num 之前能否凑出 i-num）
     * 遍历顺序：内层必须「倒序」，保证每个 num 只被使用一次（0/1 背包与完全背包的关键区别）
     * 答案：dp[target]
     * 时间复杂度：O(n * sum)
     * 空间复杂度：O(sum)
     *
     * @param nums 输入数组
     * @return 能否分割成两个和相等的子集
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        // 奇数无法平分
        if (sum % 2 == 1) return false;

        int target = sum / 2;  // 背包容量：只需凑出总和的一半，另一半自然相等

        boolean[] dp = new boolean[target + 1];  // 默认值为 false，表示「凑不出」
        dp[0] = true;  // 边界：什么都不选即可凑出 0

        // 外层物品：每个数字只能用一次（0/1 背包）
        for (int num : nums) {
            // 内层容量倒序：从 target 往下扫到 num，防止本轮 num 被自己重复使用
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        return dp[target];
    }

    public static void main(String[] args) {
        Hot416_canPartition canPartition = new Hot416_canPartition();
        int[] nums = {1, 5, 11, 5};  // 可分成 [1,5,5] 与 [11]，和均为 11，输出 true
        System.out.println(canPartition.canPartition(nums));
    }

}
