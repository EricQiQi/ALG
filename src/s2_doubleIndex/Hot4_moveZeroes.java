package s2_doubleIndex;

/**
 * 4.移动 0
 * <p>
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 *
 * 慢指针：s，一步一步向后走
 * 快指针：r，只找不为0的，和s指向的元素交换
 */
public class Hot4_moveZeroes {

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0){
            return;
        }
        int s = 0;
        for(int i=0; i<nums.length; i++){
            // 快指针找到不为0的元素
            if (nums[i] != 0){
                // 写法1
                nums[s++] = nums[i];

                // 方法2：快、慢指针不相等时才赋值
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
