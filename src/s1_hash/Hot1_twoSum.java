package s1_hash;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能重复出现。
 * 但是，数组中同一个元素不能重复出现。
 * 你可以按任意顺序返回答案。
 */
public class Hot1_twoSum {

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public static int[] twoSum(int[] nums, int target){
        if (nums == null || nums.length ==0) return new int[2];

        int[] res = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if (map.containsKey(target-nums[i])){
                res[0] = i;
                res[1] = map.get(target-nums[i]);
            }else{
                map.put(nums[i], i);
            }
        }

        return res;
    }


    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] res = twoSum(nums, target);
        System.out.println(res[0] + " " + res[1]);
    }
}
