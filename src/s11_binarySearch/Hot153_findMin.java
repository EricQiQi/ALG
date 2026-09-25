package s11_binarySearch;

/**
 * 153. 寻找旋转排序数组中的最小值
 */
public class Hot153_findMin {


    /**
     * 方法1：二分查找
     * 时间复杂度：O(logn)
     * 空间复杂度：O(1)
     */
    public int findMin_1(int[] nums) {
        int left=0, right=nums.length-1;
        int ans = nums[0];
        while(left<=right){
            int mid=left+(right-left)/2;
            if(nums[left] <= nums[mid]){
                ans = Math.min(nums[left], ans);
                left = mid+1;
            }else{
                ans = Math.min(nums[mid], ans);
                right = mid-1;
            }
        }
        return ans;
    }

    /**
     * 方法2：二分查找
     * 时间复杂度：O(logn)
     * 空间复杂度：O(1)
     */
    public int findMin_2(int[] nums) {
        int left =0, right = nums.length-1;
        while(left < right){
            int mid = left+(right-left)/2;
            if(nums[mid] > nums[right]){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        Hot153_findMin hot153_findMin = new Hot153_findMin();
        int[] nums = {4,5,6,7,0,1,2};
        int i = hot153_findMin.findMin_1(nums);
        System.out.println(i);

        int i2 = hot153_findMin.findMin_2(nums);
        System.out.println(i2);
    }
}
