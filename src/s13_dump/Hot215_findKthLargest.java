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
     * <p>
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int findKthLargest_1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 方法2：快速排序
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int findKthLargest_2(int[] nums, int k) {
        QuickSort.quickSort(nums);
        return nums[nums.length - k];
    }

    /**
     * 方法3：堆（优先队列）
     * 时间复杂度：O(nlogk)
     * 空间复杂度：O(k)
     */
    public int findKthLargest_3(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num : nums) {
            heap.offer(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.peek();
    }

    /**
     * 方法4：构建大顶堆
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(1)
     */
    public int findKthLargest_4(int[] nums, int k) {
        int n = nums.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, i, n);
        }

        System.out.println(Arrays.toString(nums));

        for (int i = 0; i < k-1; i++) {
            swap(nums, 0, n-1-i);
            heapify(nums, 0, n-1-i);
        }
        return nums[0];
    }

    private void heapify(int[] arr, int i, int heapSize) {
        while(true){
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if(left < heapSize && arr[left] > arr[largest]) largest = left;
            if(right < heapSize && arr[right] > arr[largest]) largest = right;
            if(largest == i) break;
            swap(arr, i, largest);
            i = largest;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Hot215_findKthLargest obj = new Hot215_findKthLargest();
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 3;
        System.out.println(obj.findKthLargest_1(nums, k));
        System.out.println(obj.findKthLargest_2(nums, k));
        System.out.println(obj.findKthLargest_3(nums, k));
        System.out.println(obj.findKthLargest_4(nums, k));
    }
}
