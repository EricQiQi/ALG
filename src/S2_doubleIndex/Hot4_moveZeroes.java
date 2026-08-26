package S2_doubleIndex;

/**
 * 移动0
 * 慢指针：s，一步一步向后走
 * 快指针：r，只找不为0的，和s指向的元素交换
 */
public class Hot4_moveZeroes {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0){
            return;
        }
        int s = 0;
        for(int i=0; i<nums.length; i++){
            if (nums[i] != 0){
                // 写法1
                nums[s++] = nums[i];

                // 方法2：快、慢指针不相等时才交换
//                if (s != i) {
//                    nums[s] = nums[i];
//                }
//                s++;
            }
        }
        while(s<nums.length){
            nums[s++] = 0;
        }
    }

    public static void main(String[] args) {
        Hot4_moveZeroes move0 = new Hot4_moveZeroes();
//        int[] nums = {0,1,0,3,12};
        int[] nums = {1};
        move0.moveZeroes(nums);
        for (int num : nums){
            System.out.print(num + " ");
        }
    }
}
