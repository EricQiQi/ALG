package s5_ordinaryArray;

/**
 * 53. 最大子序和
 *
 * ！！！和 Hot10-和为k的子数组 题目区别：本题目和是未知的
 * ！！！和 Hot11-滑动窗口的最大值 题目区别：本题目中窗口是未知的
 */
public class Hot13_maxSubArray {

    /**
     * 贪心算法
     * @param nums
     * @return
     */
    public static int maxSubArray_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // maxSum必须初始为nums[0]，因为nums[i]可能为负数
        int maxSum = nums[0];

        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果preSum < 0，说明preSum对当前元素nums[i]的贡献是负的，所以应该舍弃，从0开始重新计算
            if (preSum < 0){
                preSum = 0;
            }
            preSum += nums[i];
            maxSum = Math.max(maxSum, preSum);
        }
        return maxSum;
    }


    /**
     * 动态规划
     * @param nums
     * @return
     */
    public static int maxSubArray_2(int[] nums) {
        // 初始化：最大和与当前子序和都设为数组第一个元素
        int maxAns = nums[0];
        int currentSum = nums[0];

        // 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 如果 currentSum 是负数，则舍弃，直接从 nums[i] 开始
            // 如果 currentSum 是正数，则加上 nums[i]
            currentSum = Math.max(nums[i], currentSum + nums[i]);

            // 实时更新全局最大值
            maxAns = Math.max(maxAns, currentSum);
        }

        return maxAns;
    }

    public static void main(String[] args) {
        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        int[] nums1 = { -2, -1, -3};
        int[] nums2 = { 1, 2};
        System.out.println(maxSubArray_1(nums));
        System.out.println(maxSubArray_1(nums1));
        System.out.println(maxSubArray_1(nums2));

        System.out.println("-----");

        System.out.println(maxSubArray_2(nums));
        System.out.println(maxSubArray_2(nums1));
        System.out.println(maxSubArray_2(nums2));
    }
}
