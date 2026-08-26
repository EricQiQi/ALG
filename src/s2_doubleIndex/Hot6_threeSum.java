package s2_doubleIndex;

import java.util.*;

/**
 * 三数之和
 *
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。
 * 请你返回所有和为 0 且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 * <p>
 * 示例 3：
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 * <p>
 * <p>
 * 提示：
 * 3 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */
public class Hot6_threeSum {

    /**
     * HashSet解法
     * 最外层可以剪枝去重；
     * 内层相当于两数之和，将数放到set中，判断set中是否存在target-nums[j]，内层不能通过if (j > i + 1 && nums[j] == nums[j - 1]) continue;去重
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum_1(int[] nums) {
        if (nums[0] > 0) return new ArrayList<>();

        Arrays.sort(nums);

        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 剪枝
            if (nums[i] > 0) break;
            // a去重
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int target = 0 - nums[i];
            Set<Integer> set = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                if (set.contains(target - nums[j])) {
                    // 找到一个三元组
                    result.add(Arrays.asList(nums[i], target - nums[j], nums[j]));
                } else {
                    set.add(nums[j]);
                }
            }
        }
        return new ArrayList<>(result);
    }


    public List<List<Integer>> threeSum_2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 剪枝
            if (nums[i] > 0) break;
            // a去重
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int target = 0 - nums[i];

            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // b去重
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // c去重
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Hot6_threeSum hot6_threeSum = new Hot6_threeSum();
//        int[] nums = {-1,0,1,2,-1,-4};
        int[] nums = {0, 0, 0};
        System.out.println(hot6_threeSum.threeSum_1(nums));
    }

}


