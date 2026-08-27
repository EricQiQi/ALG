package s5_ordinaryArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 41. 缺失的第一个正数
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 *
 * 输入：nums = [1,2,0]
 * 输出：3
 *
 * 输入：nums = [3,4,-1,1]
 * 输出：2
 *
 * 输入：nums = [7,8,9,11,12]
 * 输出：1
 *
 * 1 <= nums.length <= 5 * 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 */
public class Hot41_firstMissingPositive {

    public int firstMissingPositive(int[] nums) {
        Arrays.sort(nums);
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num > 0) {
                set.add(num);
            }
        }
        for(int i : set){
            if (!set.contains(i-1) && i-1>0){
                return 1;
            }
        }
        return nums[nums.length-1] + 1;
    }

    public static void main(String[] args) {
        Hot41_firstMissingPositive hot41 = new Hot41_firstMissingPositive();
//        int[] nums = {1,2,0};
        int[] nums = {7,8,9,11,12};
        System.out.println(hot41.firstMissingPositive(nums));
    }
}
