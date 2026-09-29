package sort;

public class HeapSort {
    public static void heapSort(int[] arr) {
        if(arr == null || arr.length == 0) return;
        int len = arr.length;

        // 建大顶堆
        for(int i=len/2-1; i>=0; i--){
            heapify(arr, i, len);
        }

        for(int i=len-1; i>0; i--){
            swap(arr, 0, i);     // 堆顶放到末尾
            heapify(arr, 0, i);  // 调整剩余元素成堆
        }
    }

    private static void heapify(int[] arr, int i, int heapSize){
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if(left < heapSize && arr[left] > arr[largest]){
            largest = left;
        }
        if(right < heapSize && arr[right] > arr[largest]){
            largest = right;
        }
        if(largest != i){
            swap(arr, i, largest);
            heapify(arr, largest, heapSize); // 递归调整子树
        }
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 2, 4, 6, 8, 0};
        heapSort(arr);
        for(int i: arr){
            System.out.print(i + " ");
        }
    }
}
