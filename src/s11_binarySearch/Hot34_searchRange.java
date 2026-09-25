package s11_binarySearch;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 */
public class Hot34_searchRange {

    /**
     * 二分查找
     * 时间复杂度：O(logn)
     * 空间复杂度：O(1)
     */
    public int[] searchRange(int[] nums, int target) {
        int start = -1, end = -1;

        int left = 0, right = nums.length-1;

        while(left<=right){
            int mid = left + (right-left)/2;
            if(nums[mid] == target){
                start = mid;
                right = mid - 1;
            }else if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        left = 0;
        right = nums.length-1;

        while(left<=right){
            int mid = left + (right-left)/2;
            if(nums[mid] == target){
                end = mid;
                left = mid + 1;
            }else if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return new int[]{start, end};
    }

    public static void main(String[] args) {
        Hot34_searchRange hot34_searchRange = new Hot34_searchRange();
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        int[] res = hot34_searchRange.searchRange(nums, target);
        System.out.println(res[0] + " " + res[1]);
    }
}
