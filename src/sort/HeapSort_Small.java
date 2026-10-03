package sort;

/**
 * 堆排序
 * 小顶堆
 */
public class HeapSort_Small {

    public void heapSort(int[] arr) {
        if (arr == null || arr.length == 0) return;

        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }

        for(int i=0; i<n-1; i++){
            swap(arr, 0, n-1-i);
            heapify(arr, 0, n-1-i);
        }
    }


    public void heapify(int[] arr, int i, int heapsize) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heapsize && arr[left] < arr[smallest]) smallest = left;
        if (right < heapsize && arr[right] < arr[smallest]) smallest = right;

        if (smallest != i) {
            swap(arr, i, smallest);
            heapify(arr, smallest, heapsize);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }


    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 2, 4, 6, 8, 0};
        new HeapSort_Small().heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
