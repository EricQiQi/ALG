package test;

public class HeapSort {

    public static void heapSort(int[] arr) {
        int n = arr.length;
        for(int i=n/2-1; i>=0; i--){
            heapify(arr, i, n);
        }

        for(int i=0; i<n-1; i++){
            swap(arr, 0, n-1-i);
            heapify(arr, 0, n-1-i);
        }

    }

    private static void heapify(int[] arr, int i, int heapsize){
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if(left < heapsize && arr[left] > arr[largest]) largest = left;
        if(right < heapsize && arr[right] > arr[largest]) largest = right;

        if(largest != i){
            swap(arr, i, largest);
            heapify(arr, largest, heapsize);
        }
    }

    private static void swap(int[] arr, int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }


    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 2, 4, 6, 8, 0};
        heapSort(arr);
        for(int i: arr){
            System.out.print(i + " ");
        }
    }
}
