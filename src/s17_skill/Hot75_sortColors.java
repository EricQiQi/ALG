package s17_skill;

import java.util.Arrays;

/**
 * 75. 颜色分类
 *
 * 荷兰国旗问题（三指针方法）、计数排序、单指针
 * 通用排序不要用
 */
public class Hot75_sortColors {

    /**
     * 方法1：三指针
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public void sortColors_1(int[] nums) {
        int p0 = 0;
        int i = 0;
        int p2 = nums.length-1;

        while(i <= p2){
            if(nums[i] == 0){
                // 遇到 0，换到前面
                swap(nums, i, p0);
                p0++;
                i++;
            }else if(nums[i] == 1){
                // 遇到 1，不用换
                i++;
            }else{
                // 遇到 2，换到后面
                swap(nums, i, p2);
                p2--;
                // 注意：i 不前进！因为从 p2 换过来的元素还没有检查过
            }
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 方法2：计数排序
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public void sortColors_2(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        for(int num : nums){
            if(num == 0) count0++;
            else if(num == 1) count1++;
            else count2++;
        }

        int index = 0;
        for(int i=0; i<count0; i++) nums[index++] = 0;
        for(int i=0; i<count1; i++) nums[index++] = 1;
        for(int i=0; i<count2; i++) nums[index++] = 2;
    }

    /**
     * 方法3：单指针
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public void sortColors_3(int[] nums) {
        int p = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i] == 0){
                swap(nums, i, p);
                p++;
            }
        }

        for(int i=p; i<nums.length; i++){
            if(nums[i] == 1){
                swap(nums, i, p);
                p++;
            }
        }
    }

    public static void main(String[] args) {
        Hot75_sortColors hot75_sortColors = new Hot75_sortColors();
        int[] nums1 = {2, 0, 2, 1, 1, 0};
        hot75_sortColors.sortColors_1(nums1);
        System.out.println(Arrays.toString(nums1));

        int[] nums2 = {2, 0, 2, 1, 1, 0};
        hot75_sortColors.sortColors_2(nums2);
        System.out.println(Arrays.toString(nums2));

        int[] nums3 = {2, 0, 2, 1, 1, 0};
        hot75_sortColors.sortColors_3(nums3);
        System.out.println(Arrays.toString(nums3));
    }

}
