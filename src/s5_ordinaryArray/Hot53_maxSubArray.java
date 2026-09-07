package s5_ordinaryArray;

/**
 * 53. 最大子数组和
 *
 * ！！！和 Hot10-和为k的子数组 题目区别：本题目和是未知的
 * ！！！和 Hot11-滑动窗口的最大值 题目区别：本题目中窗口是未知的
 */
public class Hot53_maxSubArray {

    /**
     * 贪心算法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public static int maxSubArray_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // maxSum必须初始为nums[0]，因为nums[i]可能为负数
        int maxSum = nums[0];

        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            // preSum < 0 表示对结果无增益，把当前元素的前缀和【该元素左边的和】置为0
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
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
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

    /**
     * 分治法
     * 时间复杂度：O(n log n)  —— 每层合并需 O(n) 扫描跨中点子段，共 log n 层
     * 空间复杂度：O(log n)   —— 递归调用栈深度
     */
    public static int maxSubArray_3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return divide(nums, 0, nums.length - 1);
    }
    
    /**
     * 求 [left, right] 区间内的最大子数组和
     * 最大子数组只有三种位置：完全在左半、完全在右半、跨越中点
     */
    private static int divide(int[] nums, int left, int right) {
        // 终止条件：区间只剩一个元素，最大子数组就是它自己
        if (left == right) return nums[left];
    
        int mid = left + (right - left) / 2;
    
        // 分：递归求左半、右半的最大子数组和
        int leftMax = divide(nums, left, mid);
        int rightMax = divide(nums, mid + 1, right);
    
        // 治：求跨越中点的最大子数组和
        int crossMax = merge(nums, left, mid, right);
    
        // 三者取最大
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    public static int merge(int[] nums, int left, int mid, int right){
        // 治：求跨越中点的最大子数组和
        int sum = 0;

        //   从中点向左扩展，记录左半边的最大后缀和
        int leftCross = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftCross = Math.max(leftCross, sum);
        }

        //   从中点+1向右扩展，记录右半边的最大前缀和
        int rightCross = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightCross = Math.max(rightCross, sum);
        }
        int crossMax = leftCross + rightCross;
        return crossMax;
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

        System.out.println("-----");

        System.out.println(maxSubArray_3(nums));
        System.out.println(maxSubArray_3(nums1));
        System.out.println(maxSubArray_3(nums2));
    }
}
