package s11_binarySearch;

/**
 * 35. 搜索插入位置
 */
public class Hot35_searchInsert {

    public int searchInsert(int[] nums, int target) {
        int left = 0, right=nums.length-1;
        while(left <= right){
            int mid = left+(right-left)/2;
            if(nums[mid] > target){
                right = mid-1;
            }else if(nums[mid] < target){
                left = mid+1;
            }else{
                return mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        Hot35_searchInsert hot35 = new Hot35_searchInsert();
        int[] nums = {1,3,5,6};
        int target = 7;
        System.out.println(hot35.searchInsert(nums, target));
    }
}
