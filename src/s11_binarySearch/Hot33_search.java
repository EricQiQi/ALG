package s11_binarySearch;

/**
 * 33. 搜索旋转排序数组
 */
public class Hot33_search {


    /**
     * 二分查找
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     */
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;   // 空数组直接无解
        if (nums.length == 1) return nums[0] == target ? 0 : -1;  // 只有一个元素，比对即可

        int left = 0, right = nums.length - 1;
        while (left <= right) {                            // 闭区间 [left, right]，用 <= 才能查到最后一个元素
            int mid = left + (right - left) / 2;           // 防溢出写法，等价于 (left+right)/2
            if (nums[mid] == target) return mid;           // 命中，直接返回下标

            // 旋转数组从 mid 切开后，必有一半是完全有序的，先判断哪半有序
            if (nums[left] <= nums[mid]) {                 // 左半区 [left, mid] 有序（含 left==mid 的单元素情况）
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;                       // target 落在有序的左半区，收缩右边界
                } else {
                    left = mid + 1;                        // 否则只能去右半区找
                }
            } else {                                       // 右半区 [mid, right] 有序
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;                        // target 落在有序的右半区，收缩左边界
                } else {
                    right = mid - 1;                       // 否则去左半区找
                }
            }
        }
        return -1;                                         // 区间收缩为空仍未命中
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        System.out.println(search(nums, target));
    }
}
