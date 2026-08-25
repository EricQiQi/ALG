package s4_substring;

/**
 * 滑动窗口的最大值
 */
public class Hot11_maxSlidingWindow {

    /**
     * DP动态规划，非常好
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];
        int n = nums.length;
        int[] res = new int[n - k + 1];

        int[] left_max = new int[n];
        int[] right_max = new int[n];

        // 1. 正向扫描：算出每个位置在自己“班级（每 K 个一组）”里，从左到当前的最高分
        for (int i = 0; i < n; i++) {
            if (i % k == 0) {
                left_max[i] = nums[i]; // 班级开头第一个人
            } else {
                left_max[i] = Math.max(left_max[i - 1], nums[i]);
            }
        }

        // 2. 逆向扫描：算出每个位置在自己“班级”里，从当前到右边结尾的最高分
        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1 || (i + 1) % k == 0) {
                right_max[i] = nums[i]; // 班级最后一个结尾的人
            } else {
                right_max[i] = Math.max(right_max[i + 1], nums[i]);
            }
        }

        // 3. 一键出答案：完全不需要内层 for 循环“翻箱倒柜”，一秒查表
        for (int i = 0; i < res.length; i++) {
            int left = i;
            int right = i + k - 1;
            res[i] = Math.max(right_max[left], left_max[right]);
        }

        return res;
    }

    /**
     * 单调队列解法
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_2(int[] nums, int k){
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];
        int[] res = new int[nums.length - k + 1];



        return res;
    }

    /**
     * 错误解法：时间超过限制了，而且容易出bug
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_error1(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        int[] res = new int[nums.length - k + 1];

        int left = 0;
        int max = nums[0];

        for (int right = 0; right < nums.length; right++) {
            max = Math.max(max, nums[right]);
            if (right - left + 1 == k) {
                res[left] = max;
                left++;
                if (left < nums.length) {
                    max = nums[left];
                    for (int i = left; i <= right; i++) {
                        max = Math.max(max, nums[i]);
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
//        int[] nums = {3, 1, -1, -3, 5, 3, 6, 7};
        int[] res = maxSlidingWindow_1(nums, 3);
//        int[] nums = {1, -1};
//        int[] res = maxSlidingWindow(nums, 1);
//        int[] nums = {1};
//        int[] res = maxSlidingWindow(nums, 1);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }
}
