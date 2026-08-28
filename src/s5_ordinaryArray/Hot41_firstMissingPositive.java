package s5_ordinaryArray;

import java.util.HashSet;
import java.util.Set;

/**
 * 41. 缺失的第一个正数
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * <p>
 * 输入：nums = [1,2,0]
 * 输出：3
 * <p>
 * 输入：nums = [3,4,-1,1]
 * 输出：2
 * <p>
 * 输入：nums = [7,8,9,11,12]
 * 输出：1
 * <p>
 * 1 <= nums.length <= 5 * 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 */
public class Hot41_firstMissingPositive {

    /**
     * 笨方法
     * 时间复杂度 O(n)
     * 空间复杂度 O(n)
     * @param nums
     * @return
     */
    public int firstMissingPositive_1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num > 0) {
                set.add(num);
            }
        }
        int count = 1;
        while (set.contains(count)) {
            count++;
        }
        return count;
    }

    /**
     * 精妙方法
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     * @param nums
     * @return
     */
    public static int firstMissingPositive_2(int[] nums) {
        int n = nums.length;
        // 清理负数和0
        for(int i=0; i<n; i++){
            if(nums[i]<=0){
                nums[i] = n + 1;
            }
        }

        // 打标
        for(int i=0; i<n; i++){
            // 值 转换成 下标   注意必须要用 Math.abs,因为69行会取反
            int index = Math.abs(nums[i]);
            if(index <= n){
                // 对应下标的值 标记为负数
                nums[index - 1] = -Math.abs(nums[index - 1]);
            }
        }

        for(int i=0; i<n; i++){
            // nums[i] > 0 则表示 i+1这个数没出现过,因为出现过的都标记为负数了
            if(nums[i] > 0){
                return i+1;
            }
        }

        // 如果所有数都出现过，返回 n+1
        return n+1;
    }

    public static void main(String[] args) {
        Hot41_firstMissingPositive hot41 = new Hot41_firstMissingPositive();
//        int[] nums = {1,2,0};
//        int[] nums = {7,8,9,11,12};
        int[] nums = {2, -8, -1, 9, 11, 12};
//        System.out.println(hot41.firstMissingPositive_1(nums));
        System.out.println(hot41.firstMissingPositive_2(nums));
    }
}
