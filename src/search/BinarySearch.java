package search;

/**
 * 二分查找算法
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 */
public class BinarySearch {
    public int binarySearch(int[] nums, int target) {
        if(nums == null || nums.length ==0) return -1;

        int left = 0, right = nums.length - 1;
        // 只要 left <= right 就说明还存在搜索空间，注意等号=
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] == target){
                return mid;
            } else if (nums[mid] > target) {
                right = mid-1;
            }else{
                left = mid+1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();
        int[] nums = {1,2,3,4,5,6,7,8,9,10};
        int target = 7;
        System.out.println(bs.binarySearch(nums, target));
    }
}
