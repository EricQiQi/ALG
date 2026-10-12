package s17_skill;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 169. 多数元素
 */
public class Hot169_majorityElement {


    /**
     * 方法1：投票法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int majorityElement_1(int[] nums) {
        int candidate = 0;
        int count = 0;
        for (int num : nums) {
            if(count == 0){
                // 上一轮抵消干净，换新的候选人；count 必须同时置 1，否则永远困在 0，candidate 退化为最后一个元素
                candidate = num;
                count = 1;
            }else if(num == candidate){
                count++;
            }else{
                count--;
            }
        }
        return candidate;
    }

    /**
     * 方法2：排序
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int majorityElement_2(int[] nums) {
        Arrays.sort(nums);

        // 中位数一定是众数
        // [1,1,2,2,2] -> nums[2] = 2
        // [1,2,2,2,2] -> nums[2] = 2
        // [1,2,3,3,3,3] -> nums[2] = 3
        return nums[nums.length / 2];
    }

    /**
     * 方法3：哈希表
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int majorityElement_3(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > nums.length / 2) return num;
        }

        return -1;
    }


    public static void main(String[] args) {
        Hot169_majorityElement solution = new Hot169_majorityElement();
        int[] nums = {3, 2, 3};
        int majority1 = solution.majorityElement_1(nums);
        System.out.println(majority1); // 输出 3
        int majority2 = solution.majorityElement_2(nums);
        System.out.println(majority2); // 输出 3
        int majority3 = solution.majorityElement_3(nums);
        System.out.println(majority3); // 输出 3
    }
}
