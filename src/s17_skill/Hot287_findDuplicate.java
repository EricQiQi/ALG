package s17_skill;

/**
 * 287. 寻找重复数
 */
public class Hot287_findDuplicate {

    /**
     * 方法1：快慢指针
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public int findDuplicate_1(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        // 快慢指针找相遇点
        do{
            // 慢指针走1步
            slow = nums[slow];
            // 快指针走2步，走2步是 nums[fast] 的 nums[fast]，即 nums[fast] 的值作为下标，再走一步
            fast = nums[nums[fast]];
        }while(slow != fast);

        // 从头开始，慢指针走1步，快指针走1步，相遇点即为入口点
        slow = nums[0];
        while(slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    /**
     * 方法2：二分查找
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int findDuplicate_2(int[] nums) {
        int left = 1, right = nums.length - 1;
        while(left < right){
            int mid = left + (right-left)/2;

            int count = 0;
            for(int num : nums){
                if(num <= mid) count++;
            }

            if(count > mid){
                right = mid;
            }else{
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Hot287_findDuplicate hot287_findDuplicate = new Hot287_findDuplicate();
        int[] nums = {1, 3, 4, 2, 2};
        System.out.println(hot287_findDuplicate.findDuplicate_1(nums));
        System.out.println(hot287_findDuplicate.findDuplicate_2(nums));
    }

}
