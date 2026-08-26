package S1_hash;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 */
public class Hot1_twoSum {

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
