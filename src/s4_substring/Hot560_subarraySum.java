package s4_substring;

import java.util.HashMap;
import java.util.Map;

/**
 * 560. 和为K的子数组
 */
public class Hot560_subarraySum {

    /**
     * 前缀和 + 逆向查账
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public static int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        // 1. 记账本：Key 存过去的累计和(preSum)，Value 存这个累计和出现了几次
        Map<Integer, Integer> map = new HashMap<>();
        // 💡 极其重要的初始化：如果刚好一出发累计和就等于 K，我们需要减去 0，所以 0 默认出现了 1 次
        map.put(0, 1);

        int preSum = 0; // 当前的累计前缀和
        int count = 0;  // 满足和为 K 的子数组总个数

        // 2. 会计开始从左往右走，只扫一遍数组
        for (int num : nums) {
            // 实时累计现在的总分
            preSum += num;

            // 3. 逆向查账：看看过去有没有哪几次的累计和，刚好等于 (preSum - k)
            if (map.containsKey(preSum - k)) {
                count += map.get(preSum - k); // 以前出现过几次，就说明现在能凑出几种答案
            }

            // 4. 把现在的累计和也登记到账本里，供后面的数字查阅
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(subarraySum(new int[]{1, 2, 3}, 3));
    }


}
