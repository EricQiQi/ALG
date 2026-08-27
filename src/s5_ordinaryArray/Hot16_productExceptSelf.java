package s5_ordinaryArray;

/**
 * 16. 除自身以外数组的乘积
 * 给你一个长度为 n 的数组 nums，其中 nums[i] 不为 0。nums 中的每个元素都保证在 1 到 n 之间（包含 1 和 n）。
 * 请你返回一个长度为 n 的数组 answer 作为答案，满足 answer[i] 是 nums 中除了 nums[i] 以外其他所有元素的乘积。
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 *
 * 原理：从左往右乘一遍，从右往左乘一遍，然后把两个结果数组对应位置相乘，得到的就是答案。
 * 非常巧妙！！！
 *
 */
public class Hot16_productExceptSelf {

    /**
     * 前缀积解法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)，题目免责一个输出数组，但是还用了另外两个数组，所以是 O(n)
     * @param nums
     * @return
     */
    public int[] productExceptSelf_1(int[] nums) {
        int[] res = new int[nums.length];
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];

        // left[0] 表示 nums[0] 左边的所有元素的乘积，因为 nums[0] 没有左边的元素，所以 left[0] 为 1
        left[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        // right[n-1] 表示 nums[n-1] 右边的所有元素的乘积，因为 nums[n-1] 没有右边的元素，所以 right[n-1] 为 1
        right[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        for (int i = 0; i < nums.length; i++) {
            res[i] = left[i] * right[i];
        }
        return res;
    }


    /**
     * 双指针解法
     * 原理：双指针同步遍历，一个从左往右，一个从右往左，同步更新结果数组。
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)，题目免责一个输出数组，所以是 O(1)
     * @param nums
     * @return
     */
    public int[] productExceptSelf_2(int[] nums) {
        int n = nums.length;
        // 1. 初始化结果数组，因为要算乘积，先把所有位置都初始化为 1，不初始化得到的都是 0
        int[] res = new int[n];
        for(int i=0; i<n; i++){
            res[i] = 1;
        }

        // 2. 双指针同步遍历
        int leftProduct = 1;  // 记录从左往右走的累乘值（前缀）
        int rightProduct = 1; // 记录从右往左走的累乘值（后缀）

        int i=0; // 左指针
        int j=n-1; // 右指针

        while(i<n){
            // 左指针更新：当前位置 res[i] 乘以左边的累乘值
            res[i] = res[i] * leftProduct;
            // 更新左边累乘值，供下一个 i 使用
            leftProduct = leftProduct * nums[i];

            // 右指针更新：当前位置 res[j] 乘以右边的累乘值
            res[j] = res[j] * rightProduct;
            // 更新右边累乘值，供下一个 j 使用
            rightProduct = rightProduct * nums[j];

            // 两个指针向中间/对向移动（这里其实就是标准的线性同步推进）
            i++;
            j--;
        }

        return res;
    }

    public static void main(String[] args) {
        Hot16_productExceptSelf hot16 = new Hot16_productExceptSelf();
        int[] nums = {1,2,3,4};
//        int[] res = hot16.productExceptSelf_1(nums);
        int[] res = hot16.productExceptSelf_2(nums);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }
}
