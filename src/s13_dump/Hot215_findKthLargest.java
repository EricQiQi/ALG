package s13_dump;

import sort.QuickSort;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 215. 数组中的第K个最大元素
 */
public class Hot215_findKthLargest {

    /**
     * 方法1：排序
     * 双轴快速排序
     *
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int findKthLargest_1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length-k];
    }

    /**
     * 方法2：快速排序
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int findKthLargest_2(int[] nums, int k) {
        QuickSort.quickSort(nums);
        return nums[nums.length-k];
    }

    /**
     * 方法3：堆（优先队列）
     * 时间复杂度：O(nlogk)
     * 空间复杂度：O(k)
     */
    public int findKthLargest_3(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for(int num : nums){
            heap.offer(num);
            if(heap.size() > k){
                heap.poll();
            }
        }
        return heap.peek();
    }

    public static void main(String[] args) {
        Hot215_findKthLargest obj = new Hot215_findKthLargest();
        int[] nums = {3,2,1,5,6,4};
        int k = 2;
        System.out.println(obj.findKthLargest_1(nums, k));
        System.out.println(obj.findKthLargest_2(nums, k));
//        System.out.println(obj.findKthLargest_3(nums, k));
    }
}
