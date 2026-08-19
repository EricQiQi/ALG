package S2_doubleIndex;

/**
 * 移动0
 * 慢指针：s，一步一步向后走
 * 快指针：r，只找不为0的，和s指向的元素交换
 */
public class Move0 {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0){
            return;
        }
        int s = 0;
        for(int i=0; i<nums.length; i++){
            if (nums[i] != 0){
                // 快、慢指针不相等时才交换
                if (s != i){
                    nums[s++] = nums[i];
                }
            }
        }
        while(s<nums.length){
            nums[s++] = 0;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        Move0 move0 = new Move0();
        int[] nums = {0,1,0,3,12};
        move0.moveZeroes(nums);
        for (int num : nums){
            System.out.print(num + " ");
        }
    }
}
