package s5_ordinaryArray;

/**
 * 189. 轮转数组
 */
public class Hot189_rotate {

    /**
     * 方法1：辅助数组，时间复杂度 O(n), 空间复杂度 O(n)
     *
     * @param nums
     * @param k
     */
    public static void rotate_1(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            res[(i + k) % n] = nums[i];
        }
        System.arraycopy(res, 0, nums, 0, n);
    }

    /**
     * 方法2：循环交换，时间复杂度 O(n), 空间复杂度 O(1)
     *
     * @param nums
     * @param k
     */
    public static void rotate_2(int[] nums, int k) {
        int n = nums.length;
        // 必须要有这一步,如果 k = 1000,实际 nums=[1]
        k = k % n;

        // 1.翻转整个数组
        reverse(nums, 0, n - 1);
        // 2.翻转前k个元素
        reverse(nums, 0, k - 1);
        // 3.翻转剩余元素
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * 方法3：环状替换（原地跳跃法），时间复杂度 O(n), 空间复杂度 O(1)
     *
     * @param nums
     * @param k
     */
    public static void rotate_3(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0) return;

        int count = 0;
        for (int start = 0; count < n; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;

                current = next;
                count++;
            } while (start != current);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
//        rotate_1(nums, 3);
//        rotate_2(nums, 3);
        rotate_3(nums, 3);
        printArray(nums);
    }

    public static void printArray(int[] nums) {
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
