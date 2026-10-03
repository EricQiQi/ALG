package sort;

/**
 * 堆排序
 * 核心思想：建大顶堆，堆顶与末尾交换，缩小堆范围，重新下沉
 */
public class HeapSort_Big {

    public static void heapSort(int[] arr) {
        int n = arr.length;

        // 建堆：从最后一个非叶子节点(n/2-1)开始，自底向上逐个下沉
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }

        // 排序：每次将堆顶(最大值)换到末尾，然后缩小堆范围重新下沉
        for (int i = 0; i < n - 1; i++) {
            swap(arr, 0, n - 1 - i);
            heapify(arr, 0, n - 1 - i);
        }
    }

    /**
     * 下沉操作：将 arr[i] 下沉到正确位置，维护大顶堆性质
     * @param heapsize 当前堆的有效范围 [0, heapsize)
     */
    private static void heapify(int[] arr, int i, int heapsize) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // 左孩子比当前大，更新 largest
        if (left < heapsize && arr[left] > arr[largest]) largest = left;
        // 右孩子比当前大，更新 largest
        if (right < heapsize && arr[right] > arr[largest]) largest = right;

        // 如果最大值不是自己，交换后继续下沉
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, heapsize);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }


    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 2, 4, 6, 8, 0};
        heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
