package hash;

import java.util.HashSet;
import java.util.Set;

/**
 * 最长连续序列
 */
public class Hot3 {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        Set<Integer> set = new HashSet<>();
        for (int num : nums){
            set.add(num);
        }

        int maxLen = 0;
        for(Integer num : set){
            // 找起点
            if(!set.contains(num-1)){
                int start = num;
                int count = 1;

                while(set.contains(num+1)){
                    num += 1;
                    count += 1;
                }
                maxLen = Math.max(maxLen, count);
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        Hot3 hot3 = new Hot3();
        int[] nums = {100, 4, 200, 1, 3, 2};
        System.out.println(hot3.longestConsecutive(nums));
    }
}
