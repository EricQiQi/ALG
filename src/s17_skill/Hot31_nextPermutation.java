package s17_skill;


import java.util.Arrays;

/**
 * 31. 下一个排列
 */
public class Hot31_nextPermutation {

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length;

        // 第 1 步：从右往左，找第一个下降点 i
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = n-1;
            // 第 2 步：从右往左，找第一个比 nums[i] 大的数 j
            while(nums[j] <= nums[i]){
                j--;
            }

            // 第 3 步：交换 nums[i] 和 nums[j]
            swap(nums, i, j);
        }

        reverse(nums, i+1, n-1);
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end){
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Hot31_nextPermutation hot31_nextPermutation = new Hot31_nextPermutation();
        int[] nums = {1, 2, 3};
        hot31_nextPermutation.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}
